package com.ita.domain.controller;

import com.ita.domain.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:20 PM
 */
@RestController
public class TestController {
    @Autowired
    MessageService messageService;

    @GetMapping("test")
    public String test(String input) {
        return input;
    }

    @GetMapping("/mqtt")
    public String mqtt() {
        messageService.send("test");
        return "ok";
    }
}
