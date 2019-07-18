package com.example.myapp.mapper;

import com.example.myapp.model.TestModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    @Select("SELECT * FROM TEST WHERE user_id=#{user_id}")
    public List<TestModel> getTest(@Param("user_id")int user_id);

}
