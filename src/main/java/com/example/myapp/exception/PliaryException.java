package com.example.myapp.exception;

import com.example.myapp.ErrorCode;
import com.example.myapp.util.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class PliaryException extends RuntimeException {
  private ErrorCode errorCode;
}
