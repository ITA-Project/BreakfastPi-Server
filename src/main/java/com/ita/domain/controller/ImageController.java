package com.ita.domain.controller;

import com.ita.domain.service.ImageService;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageServiceImpl;

    public ImageController(ImageService imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    @PostMapping
    public boolean upload(@RequestParam MultipartFile file) {
        return imageServiceImpl.upload(file);
    }

    @PostMapping("/check")
    public DetectMisbehaviorResponse check(@RequestParam MultipartFile file) {
        return imageServiceImpl.check(file);
    }
}
