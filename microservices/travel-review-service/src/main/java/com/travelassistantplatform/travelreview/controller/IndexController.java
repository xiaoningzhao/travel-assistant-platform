package com.travelassistantplatform.travelreview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    // TODO: remove this sample method.
    @GetMapping("/")
    public String getIndexPageGreeting(){
        return "Hello";
    }
}
