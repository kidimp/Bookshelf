package com.chous.serviceregistry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping()
    public String hi() {
        discoveryClient.getInstances("consulClient").forEach(serviceInstance -> {
            System.out.println(serviceInstance.getServiceId());
            System.out.println(serviceInstance.getPort());
        });

        return "Hi";
    }

    @GetMapping("/health")
    public void healthCheck() {

    }
}
