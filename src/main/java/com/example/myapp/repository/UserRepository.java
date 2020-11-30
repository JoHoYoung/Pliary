package com.example.myapp.repository;

import com.example.myapp.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    void insertUser(@Param("user")User user);
    void updateUser(@Param("user")User user);
    User selectUserById(@Param("id")Integer id);
    User selectUserByOauthToken(@Param("oauthToken")String oauthToken);

}
