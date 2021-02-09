package edu.sjsu.cmpe295.sampleserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SampleServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleServiceProviderApplication.class, args);
    }

}
