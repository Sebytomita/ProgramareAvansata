/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab9.util;

/**
 *
 * @author Seby
 */

import java.io.IOException;
import java.util.logging.*;

public class LoggerUtil {

    private static final Logger logger = Logger.getLogger("JPALogger");

    static {
        try {
            Handler fileHandler = new FileHandler("log.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(true); // afișează și în consolă
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logExecution(String message, long timeMs) {
        logger.info(message + " took " + timeMs + " ms.");
    }

    public static void logException(Exception e) {
        StringBuilder sb = new StringBuilder("Exception: " + e.toString() + "\n");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append("\tat ").append(element).append("\n");
        }
        logger.severe(sb.toString());
    }

    public static Logger getLogger() {
        return logger;
    }
}
