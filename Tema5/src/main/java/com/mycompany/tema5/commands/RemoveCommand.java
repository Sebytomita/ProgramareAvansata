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

public class RemoveCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Usage: remove <name>");
        String name = args[0];
        repository.removeImage(name);
        System.out.println("Image '" + name + "' removed successfully.");
    }
}