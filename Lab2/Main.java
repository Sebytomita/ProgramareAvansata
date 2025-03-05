/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package lab2;

/**
 * @author Seby
 */

public class Main {
    public static void main(String[] args) {
        /// Create 4projects
        Project p1 = new Project("P1", "Applicatie", ProjectType.THEORETICAL);
        Project p2 = new Project("P2", "Web", ProjectType.PRACTICAL);
        Project p3 = new Project("P3", "Baza date", ProjectType.THEORETICAL);
        Project p4 = new Project("P4", "Programare", ProjectType.PRACTICAL);

        ///students
        Project[] s1Acceptable = {p1, p2};
        Project[] s2Acceptable = {p1, p3};
        Project[] s3Acceptable = {p3, p4};
        Project[] s4Acceptable = {p1, p4};

        Student s1 = new Student("S1", "Ion Ionut", s1Acceptable);
        Student s2 = new Student("S2", "Ana Andrei", s2Acceptable);
        Student s3 = new Student("S3", "Bogdan Blej", s3Acceptable);
        Student s4 = new Student("S4", "Cosmin Calin", s4Acceptable);

        System.out.println("Studenti:");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);

        System.out.println("\nProiecte:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        
    }
}
