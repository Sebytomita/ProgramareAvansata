/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5.model;

/**
 *
 * @author Seby
 */

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public record Image(String name, LocalDate date, List<String> tags, Path filePath) {
    public Image {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Image name cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (tags == null) {
            throw new IllegalArgumentException("Tags list cannot be null");
        }
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
    }
}