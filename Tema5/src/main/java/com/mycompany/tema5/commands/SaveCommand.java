/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5.commands;

/**
 *
 * @author Seby
 */

import com.mycompany.tema5.repository.ImageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Paths;

public class SaveCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Usage: save <filePath>");
        String filePath = args[0];
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Folosește formatul YYYY-MM-DD
        mapper.writeValue(Paths.get(filePath).toFile(), repository.getImages().values());
        System.out.println("Saved " + repository.getImages().size() + " images to " + filePath);
    }
}