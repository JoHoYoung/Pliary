package com.example.myapp.restApiTest;

import com.example.myapp.context.request.card.CreateCard;
import com.example.myapp.context.request.diary.CreateDiary;
import com.example.myapp.context.request.diary.UpdateDiary;
import com.example.myapp.context.request.user.Signin;
import com.example.myapp.mapper.CardMapper;
import com.example.myapp.mapper.DiaryMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.model.CardModel;
import com.example.myapp.response.BaseResponse;
import com.example.myapp.response.DataListResponse;
import com.example.myapp.response.JwtResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiaryCrudTest {
  //
  final private String testOauthKey = "API_TEST_OAUTH_KEY";
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CardMapper cardMapper;

  @Autowired
  UserMapper userMapper;

  @Autowired
  DiaryMapper diaryMapper;


  private String accessToken;

  private int testCardId;

  private int testUserId;

  @Before
  public void getAccessToken() throws Exception {
    final Signin signin = new Signin();
    signin.setOauthKey(testOauthKey);

    MvcResult mvcResult = mockMvc.perform(post("/user/signin")
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(signin)))
      .andExpect(status().isOk()).andReturn();

    final BaseResponse response = objectMapper.readValue(mvcResult.getResponse()
      .getContentAsString(), JwtResponse.class);


    this.accessToken = ((JwtResponse) response).getAccessToken();
    this.testUserId = userMapper.getUserId(this.testOauthKey);

  }

  @Test
  public void AcreateDiaryTest() throws Exception {

    CreateCard createCard = new CreateCard();

    createCard.setWaterPeriod(5);
    createCard.setName("Test Card For Diary");
    createCard.setNickname("Test Card For Diary");

    mockMvc.perform(post("/card/create")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(createCard)))
      .andExpect(status().isOk());

    List<CardModel> cards = cardMapper.readAllCard(this.testUserId);
    this.testCardId = cards.get(0).getId();

    CreateDiary createDiary = new CreateDiary();
    createDiary.setCardId(testCardId);
    createDiary.setTitle("Test title of Diary");
    createDiary.setBody("Test Body of Diary");

    //갯수 제한을 넘은경우 테스트
    mockMvc.perform(post("/diary/create")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(createDiary)))
      .andExpect(status().isOk());
  }


  @Test
  public void BreadDiaryTest() throws Exception {

    List<CardModel> cards = cardMapper.readAllCard(this.testUserId);
    this.testCardId = cards.get(0).getId();

    MvcResult result = mockMvc.perform(get("/diary/readAll")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(this.testCardId)))
      .andExpect(status().isOk()).andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    String diaryId = ((LinkedHashMap) (response.getData().get(0))).get("id").toString();

    mockMvc.perform(get("/diary/read")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", diaryId)).andExpect(status().isOk());
  }

  @Test
  public void CupdateDiaryTest() throws Exception {
    List<CardModel> cards = cardMapper.readAllCard(this.testUserId);
    this.testCardId = cards.get(0).getId();

    MvcResult result = mockMvc.perform(get("/diary/readAll")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(this.testCardId)))
      .andExpect(status().isOk()).andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    int diaryId = (int) ((LinkedHashMap) (response.getData().get(0))).get("id");

    UpdateDiary updateDiary = new UpdateDiary();
    updateDiary.setId(diaryId);
    updateDiary.setBody("DIARY TEST UPDATE BODY");
    updateDiary.setTitle("DIARY TEST UPDATE TITLE");
    updateDiary.setCardId(this.testCardId);

    mockMvc.perform(post("/diary/update")
      .header("Authorization", "Bearer " + accessToken)
      .contentType(APPLICATION_JSON_UTF8)
      .content(objectMapper.writeValueAsString(updateDiary)))
      .andExpect(status().isOk());

  }

  @Test
  public void DdeleteDiaryTest() throws Exception {
    List<CardModel> cards = cardMapper.readAllCard(this.testUserId);
    this.testCardId = cards.get(0).getId();

    MvcResult result = mockMvc.perform(get("/diary/readAll")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(this.testCardId)))
      .andExpect(status().isOk()).andReturn();

    DataListResponse response = objectMapper.readValue(result.getResponse().getContentAsString()
      , DataListResponse.class);

    int diaryId = (int) ((LinkedHashMap) (response.getData().get(0))).get("id");
    mockMvc.perform(get("/diary/delete")
      .header("Authorization", "Bearer " + accessToken)
      .param("id", Integer.toString(diaryId)))
      .andExpect(status().isOk());

    diaryMapper.dropDiary(testCardId);
    cardMapper.dropCard(testUserId);
  }

}
