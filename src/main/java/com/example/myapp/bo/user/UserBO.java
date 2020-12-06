package com.example.myapp.bo.user;

import com.example.myapp.ErrorCode;
import com.example.myapp.context.user.Session;
import com.example.myapp.context.user.SigninResult;
import com.example.myapp.exception.PliaryException;
import com.example.myapp.model.User;
import com.example.myapp.model.enumeration.DataState;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.service.JwtService;
import java.util.Date;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBO {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Transactional
    public SigninResult signin(String oauthToken) {

        User user = this.userRepository.findByOauthToken(oauthToken);

        if (Objects.isNull(user)) {
            throw new PliaryException(ErrorCode.USER_NOT_EXIST);
        }

        Session session = Session.builder().id(user.getId()).build();
        String accessToken = this.jwtService.accessToken(session.toString());
        String refreshToken = this.jwtService.refreshToken(session.toString());

        user.setRefreshToken(refreshToken);
        user.setUpdatedAt(new Date());

        return new SigninResult(accessToken, refreshToken);
    }

    @Transactional
    public SigninResult signup(User user) {
        User existUser = this.userRepository.findByOauthToken(user.getOauthToken());

        if (!Objects.isNull(existUser)) {
            throw new PliaryException(ErrorCode.DUPICATE_OAUTH_TOKEN);
        }
        user.setState(DataState.CREATED);
        this.userRepository.save(user);
        System.out.println(user);

        Session session = Session.builder().id(user.getId()).build();
        String accessToken = this.jwtService.accessToken(session.toString());
        String refreshToken = this.jwtService.refreshToken(session.toString());
        user.setRefreshToken(refreshToken);
        user.setOauthToken(accessToken);
        return new SigninResult(accessToken, refreshToken);
    }

    public User getUser(long userId) {
        return this.userRepository.getOne(userId);
    }
}
