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
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command {
    @Override
    public void execute(ImageRepository repository, String[] args) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Usage: report <outputFilePath>");
        String outputPath = args[0];

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        
        cfg.setClassForTemplateLoading(this.getClass(), "/com/mycompany/tema5/templates");

        Template template = cfg.getTemplate("report.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("images", repository.getImages().values());

        File reportFile = Paths.get(outputPath).toFile();
        try (FileWriter writer = new FileWriter(reportFile)) {
            template.process(data, writer);
        }

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(reportFile);
        } else {
            System.out.println("Report generated at: " + outputPath);
        }
    }
}