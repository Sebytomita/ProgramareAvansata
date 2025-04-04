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

public interface Command {
    void execute(ImageRepository repository, String[] args) throws Exception;
}
