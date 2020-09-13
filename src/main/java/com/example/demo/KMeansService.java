package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;

@Service
@Slf4j
public class KMeansService {

    private final Random random;
    private final int MAX_ITERATIONS_COUNT = 10000;

    public KMeansService() {
        random = new Random(new Date().getTime());
    }

    public Cluster[] cluster(Color[] pixels, int k) {
        log.info("kmeans start");
        Color[] centers = new Color[k];
        Cluster[] clusters = new Cluster[k];
        for (int i = 0; i < k; i++) {
            clusters[i] = new Cluster();
        }
        Set<Color> ce = new HashSet<>(k);
        while (ce.size() < k) {
            ce.add(pixels[random.nextInt(pixels.length)]);
        }
        Iterator<Color> iter = ce.iterator();
        for (int i = 0; i < centers.length; i++) {
            centers[i] = new Color(iter.next().getRGB());//iter.next();
            clusters[i].setCenter(centers[i]);
        }

        int e = Integer.MAX_VALUE;
        int E = 0;
        for (int ic = 0; ic < MAX_ITERATIONS_COUNT; ic++) {
            e = E;
            for (int i = 0; i < k; i++) {
                clusters[i].clear();
            }
            for (int i = 0; i < pixels.length; i++) {
                int d, min;
                int cluster = 0;
                d = min = distance(pixels[i], centers[cluster]);
                for (int j = 1; j < k; j++) {
                    d = distance(pixels[i], centers[j]);
                    if (d < min) {
                        min = d;
                        cluster = j;
                    }
                }
                E += min * min;
                clusters[cluster].add(pixels[i]);
            }
            int ravg = 0;
            int gavg = 0;
            int bavg = 0;
            for (int i = 0; i < k; i++) {
                for (Color p : clusters[i].getPixels()) {
                    ravg += p.getRed();
                    gavg += p.getGreen();
                    bavg += p.getBlue();
                }
                int size = clusters[i].getPixels().size();
                ravg /= size;
                gavg /= size;
                bavg /= size;
                centers[i] = new Color(ravg, gavg, bavg);
                clusters[i].setCenter(centers[i]);
            }
        }
        //System.out.println("end");
        log.info("kmeans end");
        return clusters;
    }

    private int distance(Color a, Color b) { //manhattan metric
        int red = Math.abs(a.getRed() - b.getRed());
        int green = Math.abs(a.getGreen() - b.getGreen());
        int blue = Math.abs(a.getBlue() - b.getBlue());
        return red + green + blue;
    }

}
