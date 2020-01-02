package com.example.myapp.mapper;

import com.example.myapp.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

  // 회원가입
  @Insert("INSERT INTO USER(id, email, token, state, createdAt, updatedAt) VALUES(#{id},#{email}, #{token}, 'C', now(), now())")
  int userSignup(@Param("id") String id,
                 @Param("email") String email,
                 @Param("token") String token);

  // 로그인
  @Select("SELECT * FROM USER where id = #{id}")
  @Results({@Result(property = "images", javaType = List.class, column = "id",
    many = @Many(select = "com.example.myapp.mapper.attachment.ProfileAttachmentMapper.readAttachment")), @Result(property = "id", column = "id")})
  UserModel getUser(@Param("id") String id);


  @Select("SELECT EXISTS(SELECT * FROM USER WHERE email = #{email})")
  int existUserEmail(@Param("email") String email);

  @Select("SELECT EXISTS(SELECT * FROM USER WHERE id = #{id})")
  int existUserId(@Param("id") String id);

  // 이메일 인증 완료 state = 'T' 변경
  @Update("UPDATE USER SET state = 'T' WHERE token = #{token}")
  int emailCertification(@Param("token") String token);

  // temp_password 저장

  @Update("UPDATE USER SET temp_password = #{temp_password} WHERE email = #{email}")
  int temp_passwordUpdate(@Param("email") String email, @Param("temp_password") String temp_password);

  // state = 'C' 변경
  @Update("UPDATE USER SET state = 'C' WHERE email = #{email}")
  int stateUpdate(@Param("email") String email);

  // 비밀번호 변경
  @Update("UPDATE USER SET password = temp_password WHERE token = #{token}")
  int passwordUpdate(@Param("token") String token);

  // 비밀번호 변경을 위한 토큰 변경
  @Update("UPDATE USER SET token = #{token} WHERE email = #{email}")
  int tokenUpdate(@Param("email") String email, @Param("token") String token);

  @Update("UPDATE USER SET state ='D' WHERE id = #{id} AND state != 'D'")
  void deleteUser(@Param("id") String id);

  @Delete("DELETE FROM USER where id = #{id}")
  void dropUserData(@Param("id")String id);

  // 중복 체크
  @Select("SELECT email FROM member where email = #{email}")
  String emailDuplicate(UserModel user);
}