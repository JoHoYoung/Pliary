package com.example.myapp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.myapp")
@PropertySource("application.properties")
public class MyappApplication {
  //private static final Log LOG = LogFactory.getLog( "com.example.myapp");
  public static void main(String[] args) {
  //  LOG.error("HELLO");
    SpringApplication.run(MyappApplication.class, args);
  }
}
