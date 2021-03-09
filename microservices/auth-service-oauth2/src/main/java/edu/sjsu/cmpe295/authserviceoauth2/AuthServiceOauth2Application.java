package edu.sjsu.cmpe295.authserviceoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServiceOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceOauth2Application.class, args);
    }

}
