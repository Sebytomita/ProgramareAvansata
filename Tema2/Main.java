/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tema2;

import java.time.LocalDate;

/**
 * @author Seby
 */
public class Main {
    public static void main(String[] args) {
        //Creare profesori si proiecte
        System.out.println("=== Creare profesori și proiecte ===");
        Teacher t1 = new Teacher("Prof Popescu", LocalDate.of(1970, 1, 1), null);
        Project[] t1Projects = {
            new Project("P1", "Aplicatie Mobile", ProjectType.PRACTICAL, t1),
            new Project("P2", "Teorie Grafuri", ProjectType.THEORETICAL, t1),
            new Project("P3", "Web Design", ProjectType.PRACTICAL, t1)
        };
        t1 = new Teacher("Prof Popescu", LocalDate.of(1970, 1, 1), t1Projects);

        Teacher t2 = new Teacher("Prof Ionescu", LocalDate.of(1965, 5, 10), null);
        Project[] t2Projects = {
            new Project("P4", "Baze de Date", ProjectType.THEORETICAL, t2),
            new Project("P5", "AI Basics", ProjectType.PRACTICAL, t2)
        };
        t2 = new Teacher("Prof Ionescu", LocalDate.of(1965, 5, 10), t2Projects);

        //Testare duplicate profesori (equals)
        Teacher t1Duplicate = new Teacher("Prof Popescu", LocalDate.of(1970, 1, 1), t1Projects);
        System.out.println("t1.equals(t1Duplicate): " + t1.equals(t1Duplicate)); // true

        Teacher[] teachers = {t1, t2};
        Project[] allProjects = new Project[t1Projects.length + t2Projects.length];
        System.arraycopy(t1Projects, 0, allProjects, 0, t1Projects.length);
        System.arraycopy(t2Projects, 0, allProjects, t1Projects.length, t2Projects.length);

        //Creare studenti cu preferinte
        System.out.println("\n=== Creare studenți ===");
        Project[] s1Acceptable = {t1Projects[0], t1Projects[2], t2Projects[1]}; // P1, P3, P5
        Project[] s2Acceptable = {t2Projects[0], t1Projects[1]}; // P4, P2
        Project[] s3Acceptable = {t1Projects[2], t2Projects[1], t1Projects[0]}; // P3, P5, P1
        Project[] s4Acceptable = {t2Projects[0], t1Projects[1], t2Projects[1]}; // P4, P2, P5

        Student s1 = new Student("S001", "Ion Pop", LocalDate.of(2000, 3, 15), s1Acceptable);
        Student s2 = new Student("S002", "Ana Maria", LocalDate.of(2001, 7, 22), s2Acceptable);
        Student s3 = new Student("S003", "Mihai Vlad", LocalDate.of(1999, 12, 1), s3Acceptable);
        Student s4 = new Student("S004", "Elena Ionescu", LocalDate.of(2000, 9, 10), s4Acceptable);

        //Testare duplicate studenti (equals)
        Student s1Duplicate = new Student("S001", "Ion Pop", LocalDate.of(2000, 3, 15), s1Acceptable);
        System.out.println("s1.equals(s1Duplicate): " + s1.equals(s1Duplicate)); // true

        Student[] students = {s1, s2, s3, s4};

        //Afisare preferinte initiale
        System.out.println("\n=== Preferințe studenți ===");
        for (Student s : students) {
            System.out.print(s.getName() + " (" + s.getRegistrationNumber() + ") acceptă: ");
            for (Project p : s.getAcceptableProjects()) {
                System.out.print(p.getProjectId() + " ");
            }
            System.out.println();
        }

        //Rezolvarea problemei
        System.out.println("\n=== Rezolvarea problemei ===");
        Problem problem = new Problem(students, teachers, allProjects);
        problem.solveGreedy();

        //Afisare rezultate alocare
        System.out.println("\n=== Rezultate alocare ===");
        for (Student s : students) {
            String projectId = (s.getAssignedProject() != null) ? s.getAssignedProject().getProjectId() : "None";
            System.out.println(s.getName() + " (" + s.getRegistrationNumber() + ") -> " + projectId);
        }

        //Afisare toate persoanele implicate
        System.out.println("\n=== Toate persoanele implicate ===");
        Person[] allPersons = problem.getAllPersons();
        for (Person p : allPersons) {
            String role = (p instanceof Student) ? "Student" : "Profesor";
            System.out.println(role + ": " + p.getName() + " (" + p.getDateOfBirth() + ")");
        }

        //Verificare proiecte asignate din perspectiva profesorilor
        System.out.println("\n=== Proiecte asignate (per profesor) ===");
        for (Teacher t : teachers) {
            System.out.println("Proiectele lui " + t.getName() + ":");
            for (Project p : t.getProposedProjects()) {
                String studentName = (p.getAssignedStudent() != null) ? p.getAssignedStudent().getName() : "Neasignat";
                System.out.println("  " + p.getProjectId() + " - " + p.getTitle() + " -> " + studentName);
            }
        }
    }
}
