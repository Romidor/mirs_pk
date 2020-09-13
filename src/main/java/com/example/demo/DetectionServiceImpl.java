package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Set;

@Service
@Slf4j
public class DetectionServiceImpl implements DetectionService {

    private final KMeansService kMeansService;
    private final int K = 2;

    public DetectionServiceImpl(KMeansService kMeansService) {
        this.kMeansService = kMeansService;
    }

    @Override
    public boolean hasTrees(Image past, Image current) {
        log.debug("past w = " + past.getImage().getWidth() + ", h = " + past.getImage().getHeight());
        log.debug("current w = " + current.getImage().getWidth() + ", h = " + current.getImage().getHeight());
        Cluster[] imgPastClusters;
        Color[] pastImgPixels = getPixels(past);
        imgPastClusters = kMeansService.cluster(pastImgPixels, K);

        Cluster[] imgCurrentClusters;
        Color[] currentImgPixels = getPixels(current);
        imgCurrentClusters = kMeansService.cluster(currentImgPixels, K);

        for (int i = 0; i < K; i++) {
            boolean same = false;
            for (int j = 0; j < K; j++) {
                if (imgCurrentClusters[i].getPixels().size() == imgPastClusters[i].getPixels().size()) {
                    same = true;
                    break;
                }
            }
            if (!same) return true; //true means trees are in that area
        }
        return false;
    }

    private Color[] getPixels(Image image) {
        int imgSize = image.getImage().getHeight() * image.getImage().getWidth();
        Color[] pixels = new Color[imgSize];
        for (int i = 0; i < image.getImage().getWidth(); i++) {
            for (int j = 0; j < image.getImage().getHeight(); j++) {
                int index = i * image.getImage().getHeight() + j;
                log.debug("array indexes: i = " + i + ", j = " + j + ", index = " + index);
                pixels[index] = new Color(image.getImage().getRGB(i, j));
            }
        }
        return pixels;
    }
}
