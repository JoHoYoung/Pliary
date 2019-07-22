package com.example.myapp.mapper;

import com.example.myapp.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    // 회원가입
    @Insert("INSERT INTO USER(uid, email, token) VALUES(#{uid},#{email}, #{token})")
    public int userSignup(@Param("uid")String email,
                          @Param("email")String password,
                          @Param("token")String token);

    // 로그인
    @Select("SELECT * FROM USER where email = #{email}" )
    public UserModel getUser(@Param("email") String email);

    // 이메일 인증 완료 state = 'T' 변경
    @Update("UPDATE USER SET state = 'T' WHERE token = #{token}")
    public int emailCertification(String token);

    // temp_password 저장
    @Update("UPDATE USER SET temp_password = #{temp_password} WHERE email = #{email}")
    public int temp_passwordUpdate(String email, String temp_password);

    // state = 'C' 변경
    @Update("UPDATE USER SET state = 'C' WHERE email = #{email}")
    public int stateUpdate(String email);

    // 비밀번호 변경
    @Update("UPDATE USER SET password = temp_password WHERE token = #{token}")
    public int passwordUpdate(String token);

    // 비밀번호 변경을 위한 토큰 변경
    @Update("UPDATE USER SET token = #{token} WHERE email = #{email}")
    public int tokenUpdate(String email, String token);

    // 중복 체크
    @Select("SELECT email FROM member where email = #{email}")
    public String emailDuplicate(UserModel user);
}