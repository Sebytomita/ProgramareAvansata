/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.repository;

/**
 *
 * @author Seby
 */

import com.mycompany.lab5.exceptions.ImageRepositoryException;
import com.mycompany.lab5.exceptions.ImageDisplayException;
import com.mycompany.lab5.exceptions.ImageRepositoryException;
import com.mycompany.lab5.model.Image;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageRepository {
    private final Map<String, Image> images;

    public ImageRepository() {
        this.images = new HashMap<>();
    }

    public void addImage(Image image) throws ImageRepositoryException {
        if (images.containsKey(image.name())) {
            throw new ImageRepositoryException("An image with the name '" + image.name() + "' already exists in the repository");
        }
        images.put(image.name(), image);
    }

    public void displayImage(String imageName) throws ImageRepositoryException, ImageDisplayException {
        Image image = images.get(imageName);
        if (image == null) {
            throw new ImageRepositoryException("Image with name '" + imageName + "' not found in the repository");
        }

        File imageFile = image.filePath().toFile();
        if (!imageFile.exists()) {
            throw new ImageDisplayException("Image file does not exist at path: " + image.filePath());
        }

        if (!Desktop.isDesktopSupported()) {
            throw new ImageDisplayException("Desktop is not supported on this system");
        }

        try {
            Desktop.getDesktop().open(imageFile);
        } catch (IOException e) {
            throw new ImageDisplayException("Failed to display image: " + imageName, e);
        }
    }

    public int size() {
        return images.size();
    }
}