package com.example.myapp.controller.test;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    @Select("SELECT * FROM TEST WHERE userId=#{userId}")
    public List<TestModel> getTest(@Param("userId")int userId);

}
