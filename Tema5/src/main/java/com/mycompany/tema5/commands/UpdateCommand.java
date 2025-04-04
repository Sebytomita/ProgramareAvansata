/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5.commands;

/**
 *
 * @author Seby
 */

import com.mycompany.tema5.model.Image;
import com.mycompany.tema5.repository.ImageRepository;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

public class UpdateCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 4) throw new IllegalArgumentException("Usage: update <name> <newPath> <newTag1,newTag2,...>");
        String name = args[0];
        Path newPath = Path.of(args[1]);
        String[] newTags = args[2].split(",");
        Image updatedImage = new Image(name, LocalDate.now(), Arrays.asList(newTags), newPath);
        repository.updateImage(name, updatedImage);
        System.out.println("Image '" + name + "' updated successfully.");
    }
}