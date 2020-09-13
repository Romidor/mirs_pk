package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hastrees")
@Slf4j
public class TreesController {

    private final ImageRepository imageRepository;
    private final DetectionService detectionService;

    public TreesController(DetectionService detectionService, ImageRepository imageRepository) {
        this.detectionService = detectionService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/test")
    public Boolean hasTrees(@RequestParam String imgId1, @RequestParam String imgId2) {
        log.debug("has trees");
        Image img1 = imageRepository.get(imgId1);
        Image img2 = imageRepository.get(imgId2);
        Boolean result = detectionService.hasTrees(img1, img2);
        log.debug("hasTrees result = " + result);
        return result;
    }

}
