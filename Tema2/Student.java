/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tema2;

import java.time.LocalDate;

/**
 * @author Seby
 */
public class Student extends Person {
    private String registrationNumber;
    private Project[] acceptableProjects;
    private Project assignedProject;

    public Student(String registrationNumber, String name, LocalDate dateOfBirth, Project[] acceptableProjects) {
        super(name, dateOfBirth);
        this.registrationNumber = registrationNumber;
        this.acceptableProjects = acceptableProjects != null ? acceptableProjects.clone() : new Project[0];
        this.assignedProject = null;
    }

    public String getRegistrationNumber() { return registrationNumber; }
    public Project[] getAcceptableProjects() { return acceptableProjects.clone(); }
    public Project getAssignedProject() { return assignedProject; }
    public void setAssignedProject(Project project) { this.assignedProject = project; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return super.equals(obj) && registrationNumber.equals(other.registrationNumber);
    }
}
