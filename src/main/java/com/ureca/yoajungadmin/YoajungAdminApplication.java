package com.ureca.yoajungadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class YoajungAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(YoajungAdminApplication.class, args);
    }
}
