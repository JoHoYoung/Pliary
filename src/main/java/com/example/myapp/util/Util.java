package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.NotFoundException;
import com.example.myapp.exception.UnauthorizedAccessException;

public class Util {

  public static void DataAthorization(String userId, String sessionId){
    if(!(userId.equals(sessionId))){
      if (userId == null) {
        throw new NotFoundException(ErrorCode.DATA_NOT_FOUND);
      }
      throw new UnauthorizedAccessException(ErrorCode.DATA_ACCESS_UNAUTHORIZED);
    }
  }

}
