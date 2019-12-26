package com.example.myapp.util;

import com.example.myapp.ErrorCode;
import com.example.myapp.exception.DateParseException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateHelper {
  private String baseForamt = "yyyy-MM-dd HH:mm:ss";

  public String getDateToStringFormat(String format, Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(time);
  }

  public String getDateToStringFormat(String format) {
    SimpleDateFormat transFormat = new SimpleDateFormat(format);
    return transFormat.format(new Date());
  }

  public String getDateToString() {
    SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
    return transFormat.format(new Date());
  }

  public String getDateToString(Date time) {
    SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
    return transFormat.format(time);
  }

  public Date getStringToDateFormat(String format, String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(format);
      return transFormat.parse(time);
    } catch (ParseException e) {
      throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
    }
  }

  public Date getStingToDate(String time) {
    try {
      SimpleDateFormat transFormat = new SimpleDateFormat(this.baseForamt);
      return transFormat.parse(time);
    } catch (ParseException e) {
      throw new DateParseException(ErrorCode.DATE_PARSE_ERROR);
    }
  }
}
