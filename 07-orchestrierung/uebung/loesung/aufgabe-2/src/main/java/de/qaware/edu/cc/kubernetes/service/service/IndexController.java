package de.qaware.edu.cc.kubernetes.service.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping
    public String index() {
        return "Hello World";
    }
}
