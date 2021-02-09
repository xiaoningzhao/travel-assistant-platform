package edu.sjsu.cmpe295.sampleserviceclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "sample-service-provider")
public interface SampleServiceProviderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/sample-provider")
    String getName();
}
