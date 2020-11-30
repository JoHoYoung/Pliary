package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.PliaryException;

public class Util {

  public static void numberDataAthorization(int userId, int sessionId){
    if(!(userId == sessionId)){
      if (userId == 0) {
        throw new PliaryException(ErrorCode.DATA_NOT_FOUND);
      }
      throw new PliaryException(ErrorCode.DATA_ACCESS_UNAUTHORIZED);
    }
  }

}
