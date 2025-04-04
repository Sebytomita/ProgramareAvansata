/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tema5;

/**
 *
 * @author Seby
 */

import com.mycompany.tema5.commands.*;
import com.mycompany.tema5.exceptions.InvalidCommandException;
import com.mycompany.tema5.repository.ImageRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("add", new AddCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("update", new UpdateCommand());
        commands.put("load", new LoadCommand());
        commands.put("save", new SaveCommand());
        commands.put("report", new ReportCommand());
    }

    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Image Repository Shell. Type 'exit' to quit.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.split("\\s+");
                String commandName = parts[0].toLowerCase();
                String[] commandArgs = parts.length > 1 ? java.util.Arrays.copyOfRange(parts, 1, parts.length) : new String[0];

                Command command = commands.get(commandName);
                if (command == null) throw new InvalidCommandException("Unknown command: " + commandName);

                command.execute(repository, commandArgs);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}