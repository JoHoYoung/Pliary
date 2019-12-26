package com.example.myapp.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomString {

  public String generate(){
    char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
    StringBuffer sb = new StringBuffer();
    // 토큰 생성
    Random rn = new Random();
    for( int i = 0 ; i < 13 ; i++ ){
      sb.append( charaters[ rn.nextInt( charaters.length ) ] );
    }
    return sb.toString();
  }

}
