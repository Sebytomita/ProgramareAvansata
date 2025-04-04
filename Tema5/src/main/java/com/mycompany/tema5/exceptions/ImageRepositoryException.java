/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5.exceptions;

/**
 *
 * @author Seby
 */

public class ImageRepositoryException extends RuntimeException {
    public ImageRepositoryException(String message) {
        super(message);
    }

    public ImageRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}