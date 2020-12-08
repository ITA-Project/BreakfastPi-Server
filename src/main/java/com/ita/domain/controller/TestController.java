package com.ita.domain.controller;

import com.ita.domain.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dillon Xie
 * @date 11/13/2020 5:20 PM
 */
@RestController
public class TestController {

    private static final String SUCCESS = "SUCCESS";

    @Autowired
    MessageService messageService;

    @GetMapping("test")
    public String test() {
        return SUCCESS;
    }

    @GetMapping("/mqtt/{boxId}")
    public String mqtt(@PathVariable String boxId) {
        messageService.send(boxId);
        return "ok";
    }
}
