package com.example.myapp.controller.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


// Log Test
@RestController
public class LogTestController {

    private static final Logger LOG = LogManager.getLogger(LogTestController.class);

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public String test() {
        LOG.info("Info level log");
        LOG.error("Error level log");
        return "hello :)";
    }

}