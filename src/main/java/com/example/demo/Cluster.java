package com.example.demo;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

@Data
public class Cluster {

    private java.util.List<Color> pixels = new ArrayList<>();
    private Color center;

    public void add(Color c) {
        pixels.add(c);
    }

    public void remove(Color c) {
        pixels.remove(c);
    }

    public boolean contains(Color c) {
        return pixels.contains(c);
    }

    public void clear() {
        pixels.clear();
    }

}
