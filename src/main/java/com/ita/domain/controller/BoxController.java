package com.ita.domain.controller;

import com.ita.domain.entity.Box;
import com.ita.domain.service.BoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    private final BoxService boxServiceImpl;

    public BoxController(BoxService boxServiceImpl) {
        this.boxServiceImpl = boxServiceImpl;
    }

    @GetMapping("/{id}")
    public Box selectBoxById(@PathVariable Integer id) {
        return boxServiceImpl.selectById(id);
    }

    @GetMapping
    public List<Box> selectAll() {
        return boxServiceImpl.selectAll();
    }

    @GetMapping("/status/{status}")
    public List<Box> selectByStatus(@PathVariable Integer status) {
        return boxServiceImpl.selectByStatus(status);
    }

    @PostMapping
    public int create(@RequestBody Box box) {
        return boxServiceImpl.create(box);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) {
        return boxServiceImpl.delete(id);
    }

    @PutMapping
    public int update(@RequestBody Box box) {
        return boxServiceImpl.update(box);
    }

    @PutMapping("/open")
    public ResponseEntity<Boolean> updateBoxesStatus(@RequestBody List<Integer> orderIds) {
        return ResponseEntity.ok(boxServiceImpl.openAssociateOrdersBoxes(orderIds));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> updateBoxStatusById(@PathVariable Integer id, @RequestParam Integer status) {
        return ResponseEntity.ok(boxServiceImpl.updateStatusById(id, status));
    }
}
