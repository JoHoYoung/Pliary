package com.example.myapp.controller.test;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper {

    @Select("SELECT * FROM USER WHERE id=#{userId}")
    @Results({@Result(property = "list", javaType = List.class, column = "id",
                    many = @Many(select = "com.example.myapp.controller.test.TestMapper.getTest"))})
    public GroupModel test(@Param("userId")int userId);

}
