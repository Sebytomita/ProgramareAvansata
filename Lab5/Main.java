/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;

/**
 *
 * @author Seby
 */

import com.mycompany.lab5.exceptions.ImageDisplayException;
import com.mycompany.lab5.exceptions.ImageRepositoryException;
import com.mycompany.lab5.model.Image;
import com.mycompany.lab5.repository.ImageRepository;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        
        ImageRepository repository = new ImageRepository();

        try {
            
            Path imagePath = Path.of("D:/Eu/ProgramareAvansata/Lab5/img1.png"); 
            Image image = new Image(
                "SampleImage",
                LocalDate.now(),
                Arrays.asList("nature", "landscape", "vacation"),
                imagePath
            );
            repository.addImage(image);
            System.out.println("Image added successfully: " + image.name());
            System.out.println("Repository size: " + repository.size());

            repository.displayImage("SampleImage");
            System.out.println("Image displayed successfully");
        } catch (ImageRepositoryException e) {
            System.err.println("Repository error: " + e.getMessage());
        } catch (ImageDisplayException e) {
            System.err.println("Display error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid image data: " + e.getMessage());
        }
    }
}