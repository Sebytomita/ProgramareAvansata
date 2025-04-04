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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Paths;
import java.util.List;

public class LoadCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Usage: load <filePath>");
        String filePath = args[0];
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); 
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            
            List<Image> images = mapper.readValue(
                Paths.get(filePath).toFile(),
                mapper.getTypeFactory().constructCollectionType(List.class, Image.class)
            );
            
            for (Image image : images) {
                repository.addImage(image);
            }
         
            System.out.println("Loaded " + images.size() + " images from " + filePath);
        } catch (Exception e) {
            throw new Exception("Failed to load images from " + filePath + ": " + e.getMessage(), e);
        }
    }
}