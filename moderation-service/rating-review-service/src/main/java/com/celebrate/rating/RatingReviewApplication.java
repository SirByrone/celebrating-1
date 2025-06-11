package com.celebrate.rating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RatingReviewApplication {
    public static void main(String[] args) {
        SpringApplication.run(RatingReviewApplication.class, args);
    }
} 