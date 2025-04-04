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

public class AddCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 4) throw new IllegalArgumentException("Usage: add <name> <path> <tag1,tag2,...>");
        String name = args[0];
        Path path = Path.of(args[1]);
        String[] tags = args[2].split(",");
        Image image = new Image(name, LocalDate.now(), Arrays.asList(tags), path);
        repository.addImage(image);
        System.out.println("Image '" + name + "' added successfully.");
    }
}