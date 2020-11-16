package com.ita.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:20 PM
 */
@RestController
public class TestController {
    @GetMapping("test")
    public String test(String input) {
        return input;
    }
}
