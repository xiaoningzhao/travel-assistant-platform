package edu.sjsu.cmpe295.sampleserviceclient.controller;

import edu.sjsu.cmpe295.sampleserviceclient.client.SampleServiceProviderClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample-client")
public class SampleServiceClientController {

    private final SampleServiceProviderClient sampleServiceProviderClient;

    public SampleServiceClientController(SampleServiceProviderClient sampleServiceProviderClient) {
        this.sampleServiceProviderClient = sampleServiceProviderClient;
    }

    @GetMapping("")
    public String getGreeting() {
        return "Hello " + sampleServiceProviderClient.getName();
    }
}
