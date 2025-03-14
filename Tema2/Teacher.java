/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tema2;

import java.time.LocalDate;

/**
 * @author Seby
 */
public class Teacher extends Person {
    private Project[] proposedProjects;

    public Teacher(String name, LocalDate dateOfBirth, Project[] proposedProjects) {
        super(name, dateOfBirth);
        this.proposedProjects = proposedProjects != null ? proposedProjects.clone() : new Project[0];
    }

    public Project[] getProposedProjects() { return proposedProjects.clone(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Teacher)) return false;
        return super.equals(obj);
    }
}
