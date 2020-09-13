package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class ImageRepositoryMock implements ImageRepository {

    @Override
    public Image get(String id) {
        log.info("get image with id" + id);
        try (InputStream is = new FileInputStream(id + ".PNG")) {
            Image image = new Image();
            image.setImage(ImageIO.read(is));
            log.info("image found");
            return image;
        } catch (IOException e) {
            log.error("image not found");
            e.printStackTrace();
        }
        return null;
    }
}
