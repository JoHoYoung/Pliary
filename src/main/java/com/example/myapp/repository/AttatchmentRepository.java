package com.example.myapp.repository;

import com.example.myapp.model.Attatchment;
import com.example.myapp.model.enumeration.AttatchmentType;
import org.springframework.stereotype.Repository;

@Repository
public interface AttatchmentRepository {

    void insert(Attatchment attatchment);
    void delete(Integer id);
    void selectByTargetIdAndType(AttatchmentType attatchmentType, Integer targetId);
    void selectUserIdById(Integer id);

}
