package com.example.demo.dummy;


import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DummyController {

    @Secured("ROLE_USER")
    @PostMapping
    public String hello() {

        return "Hello from secured Endpoint";
    }
}
