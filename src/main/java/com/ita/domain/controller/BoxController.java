package com.ita.domain.controller;

import com.ita.domain.entity.Box;
import com.ita.domain.service.BoxService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping("/{id}")
    public Box selectBoxById(@PathVariable Integer id) {
        return boxService.selectById(id);
    }

    @GetMapping
    public List<Box> selectAll() {
        return boxService.selectAll();
    }

    @PostMapping
    public int create(@RequestBody Box box) {
        return boxService.create(box);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return boxService.delete(id);
    }

    @PutMapping
    public int update(@RequestBody Box box) {
        return boxService.update(box);
    }

}
