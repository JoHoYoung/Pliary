package com.example.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.myapp")
@PropertySource("application.properties")
public class MyappApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyappApplication.class, args);
  }
}
