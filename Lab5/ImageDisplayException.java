/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5.exceptions;

/**
 *
 * @author Seby
 */

public class ImageDisplayException extends Exception {
    public ImageDisplayException(String message) {
        super(message);
    }

    public ImageDisplayException(String message, Throwable cause) {
        super(message, cause);
    }
}