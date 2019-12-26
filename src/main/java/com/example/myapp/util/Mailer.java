package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.EmailSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class Mailer {
  @Autowired
  private JavaMailSender emailSender;

  public void sendVerfyMail(String email, String token) {
    try{
      StringBuffer text = new StringBuffer();
      text.append("<html><body><p>인증을 위해 아래 링크를 클릭해 주세요</p>");
      text.append(token);
      text.append("'>여기를 눌러 인증해주세요</a></body></html>");
      MimeMessage msg = emailSender.createMimeMessage();
      MimeMessageHelper helper;
      helper = new MimeMessageHelper(msg, false, "UTF-8");
      helper.setTo(email);
      helper.setSubject("Team Groot - 이메일 인증");
      helper.setText(text.toString(), true);
      emailSender.send(msg);
      return;
    } catch(MessagingException e){
      throw new EmailSendException(ErrorCode.EMAIL_SEND_ERROR);
    }
  }
}