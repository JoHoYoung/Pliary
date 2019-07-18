package com.example.myapp.mapper;

import com.example.myapp.model.GroupModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMapper {

    @Select("SELECT * FROM USER WHERE uid=#{user_id}")
    @Results({@Result(property = "list", javaType = List.class, column = "uid",
                    many = @Many(select = "com.example.myapp.mapper.TestMapper.getTest"))})
    public GroupModel test(@Param("user_id")int user_id);

}
