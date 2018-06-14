package com.example.reservationservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class DescriptionRestController {

    @Value("${info.description}")
    private String description;

    @RequestMapping(path = "/description")
    String getDescription() {
        return this.description;
    }
}