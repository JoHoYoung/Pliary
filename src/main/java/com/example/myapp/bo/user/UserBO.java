package com.example.myapp.bo.user;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.user.Session;
import com.example.myapp.context.user.SigninResult;
import com.example.myapp.exception.PliaryException;
import com.example.myapp.model.User;
import com.example.myapp.model.enumeration.DataState;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Service
public class UserBO {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Transactional
    public SigninResult signin(String oauthToken){

        User user = userRepository.selectUserByOauthToken(oauthToken);

        if (Objects.isNull(user)) {
            throw new PliaryException(ErrorCode.USER_NOT_EXIST);
        }

        Session session = Session.builder().id(user.getId()).build();
        String accessToken = jwtService.accessToken(session.toString());
        String refreshToken = jwtService.refreshToken(session.toString());

        user.setRefreshToken(refreshToken);
        user.setUpdatedAt(new Date());
        userRepository.updateUser(user);
        return new SigninResult(accessToken, refreshToken);
    }

    @Transactional
    public SigninResult signup(User user){
        User existUser = userRepository.selectUserByOauthToken(user.getOauthToken());

        if(!Objects.isNull(existUser)){
            throw new PliaryException(ErrorCode.DUPICATE_OAUTH_TOKEN);
        }
        user.setState(DataState.CREATED);
        userRepository.insertUser(user);
        System.out.println(user);

        Session session = Session.builder().id(user.getId()).build();
        String accessToken = jwtService.accessToken(session.toString());
        String refreshToken = jwtService.refreshToken(session.toString());
        user.setRefreshToken(refreshToken);
        userRepository.updateUser(user);

        return new SigninResult(accessToken, refreshToken);
    }
}
