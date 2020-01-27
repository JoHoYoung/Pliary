package com.example.myapp.controller.user;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.context.request.user.Signup;
import com.example.myapp.context.user.Session;
import com.example.myapp.exception.DateParseException;
import com.example.myapp.exception.InvalidAuthException;
import com.example.myapp.exception.InvalidEmailException;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.mapper.FeedMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.mapper.attachment.CardAttachmentMapper;
import com.example.myapp.mapper.attachment.DiaryAttachmentMapper;
import com.example.myapp.mapper.attachment.ProfileAttachmentMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.model.DiaryModel;
import com.example.myapp.model.UserModel;
import com.example.myapp.model.attachment.AttachmentModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataResponse;
import com.example.myapp.response.JwtResponse;
import com.example.myapp.service.JwtService;
import com.example.myapp.service.MailerService;
import com.example.myapp.util.RandomString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserCrud {

  @Autowired
  Environment env;

  @Autowired
  private JwtService jwtService;
  @Autowired
  private MailerService mailerService;
  @Autowired
  CardAttachmentMapper cardAttachmentMapper;
  @Autowired
  DiaryAttachmentMapper diaryAttachmentMapper;
  @Autowired
  ProfileAttachmentMapper profileAttachmentMapper;

  @Autowired
  CardMapper cardMapper;
  @Autowired
  DiaryMapper diaryMapper;
  @Autowired
  FeedMapper feedMapper;
  @Autowired
  UserMapper userMapper;

  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> Signup(@RequestBody Signup param) {

    // Duplicate Check
    if (userMapper.existUserEmail(param.getEmail()) != 0) {
      throw new InvalidEmailException(ErrorCode.DUPLICATED_EMAIL);
    }
    if (userMapper.existUserOauthKey(param.getOauthKey()) != 0) {
      throw new InvalidAuthException(ErrorCode.USER_ALREADY_SIGNUP);
    }

    // Generate Token for Email Auth
    String token = RandomString.generate();
    // Send verification mail, and insert to DB
    mailerService.sendVerfyMail(param.getEmail(), token);
    userMapper.userSignup(param.getEmail(), token, param.getOauthKey());

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<BaseResponse> signIn(@RequestBody Signin param) {

    // Uid Valid Check
    if (userMapper.existUserOauthKey(param.getOauthKey()) == 0) {
      throw new InvalidEmailException(ErrorCode.INVALID_EMAIL);
    }

    int userId = userMapper.getUserId(param.getOauthKey());
    // Gen Token
    JSONObject Session = new JSONObject();
    Session.put("id", userId);

    final BaseResponse response = new JwtResponse(HttpStatus.OK.value(), "success",
      jwtService.accessToken(Session.toString()),
      jwtService.refreshToken(Session.toString()));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @RequestMapping(value = "/info", method = RequestMethod.GET)
  public ResponseEntity userInfo(@RequestAttribute("session") Session session) {
    UserModel user = userMapper.getUser(session.getId());
    final BaseResponse response = new DataResponse(200, "success", user);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @Transactional
  @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
  public ResponseEntity userWithdraw(@RequestAttribute("session") Session session) {
    int userId = session.getId();
    // 프로필 삭제
    List<AttachmentModel> userProfileAttachment = profileAttachmentMapper.readAttachment(userId);
    for (AttachmentModel profileAttachment : userProfileAttachment) {
      profileAttachmentMapper.deleteAttachment(profileAttachment.getId());
      // #TODO: 사진 삭제에따른 추가 작업 ( S3에서 삭제? )
    }

    // 카드 삭제
    List<CardModel> cardModels = cardMapper.readAllCard(userId);


    for (CardModel cardModel : cardModels) {
      List<AttachmentModel> cardAttachments = cardAttachmentMapper.readAttachment(cardModel.getId());


      for (AttachmentModel cardAttachment : cardAttachments) {
        cardAttachmentMapper.deleteAttachment(cardAttachment.getId());
        // #TODO: 사진 삭제에따른 추가 작업 ( S3에서 삭제? )
      }

      feedMapper.deleteFeedFromCardId(cardModel.getId());
      List<DiaryModel> diaryModels = diaryMapper.readAllDiary(cardModel.getId());

      for (DiaryModel diaryModel : diaryModels) {
        List<AttachmentModel> diaryAttachments = diaryAttachmentMapper.readAttachment(diaryModel.getId());
        for (AttachmentModel diaryAttachment : diaryAttachments) {
          diaryAttachmentMapper.deleteAttachment(diaryAttachment.getId());
          // #TODO: 사진 삭제에따른 추가 작업 ( S3에서 삭제? )
        }
      }
      diaryMapper.deleteDiaryFromCardId(cardModel.getId());
      cardMapper.deleteCard(cardModel.getId());
    }

    userMapper.deleteUser(userId);

    final BaseResponse response = new BaseResponse(200, "success");
    return new ResponseEntity(response, HttpStatus.OK);
  }


}
