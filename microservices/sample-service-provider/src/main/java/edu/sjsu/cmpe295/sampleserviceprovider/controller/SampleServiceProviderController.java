package edu.sjsu.cmpe295.sampleserviceprovider.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sample-provider")
public class SampleServiceProviderController {

    @GetMapping("")
    public String getName() {
        return "Tom";
    }
}
