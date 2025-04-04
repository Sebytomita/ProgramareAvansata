/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.tema5.exceptions.ImageRepositoryException;
import com.mycompany.tema5.model.Image;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImageRepository {
    private final Map<String, Image> images;

    public ImageRepository() {
        this.images = new HashMap<>();
    }

    public void addImage(Image image) throws ImageRepositoryException {
        if (images.containsKey(image.name())) {
            throw new ImageRepositoryException("Image '" + image.name() + "' already exists");
        }
        images.put(image.name(), image);
    }

    public void removeImage(String name) throws ImageRepositoryException {
        if (!images.containsKey(name)) {
            throw new ImageRepositoryException("Image '" + name + "' not found");
        }
        images.remove(name);
    }

    public void updateImage(String name, Image newImage) throws ImageRepositoryException {
        if (!images.containsKey(name)) {
            throw new ImageRepositoryException("Image '" + name + "' not found");
        }
        images.put(name, newImage);
    }

    public void loadFrom(ImageRepository other) {
        images.clear();
        images.putAll(other.images);
    }

    public List<Image> getAllImages() {
        return images.values().stream().collect(Collectors.toList());
    }
    
    public Map<String, Image> getImages() {
        return new HashMap<>(images); // Returnează o copie pentru siguranță
    }

    public void displayImage(String imageName) throws Exception {
        Image image = images.get(imageName);
        if (image == null) throw new ImageRepositoryException("Image '" + imageName + "' not found");
        File file = image.filePath().toFile();
        if (!file.exists()) throw new ImageRepositoryException("File not found: " + image.filePath());
        Desktop.getDesktop().open(file);
    }

    public int size() {
        return images.size();
    }
}