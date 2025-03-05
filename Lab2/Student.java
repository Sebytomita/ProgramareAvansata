/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package lab2;

/**
 * @author Seby
 */

public class Student {
    private String studentId; 
    private String name;
    private Project assignedProject;
    private Project[] acceptableProjects; 

    public Student(String studentId, String name, Project[] acceptableProjects) {
        this.studentId = studentId;
        this.name = name;
        if(acceptableProjects != null)
        {
            this.acceptableProjects=acceptableProjects.clone();
        }
        else
            this.acceptableProjects=new Project[0];
            
        this.assignedProject = null;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }

    public Project[] getAcceptableProjects() {
        return acceptableProjects.clone(); 
    }

    public void setAcceptableProjects(Project[] acceptableProjects) {
        if(acceptableProjects != null)
        {
            this.acceptableProjects=acceptableProjects.clone();
        }
        else
            this.acceptableProjects=new Project[0];
    }

    @Override
    public String toString() {
        StringBuilder acceptableProjectsStr = new StringBuilder("[");
        for (int i = 0; i < acceptableProjects.length; i++) {
            acceptableProjectsStr.append(acceptableProjects[i].getProjectId());
            if (i < acceptableProjects.length - 1) {
                acceptableProjectsStr.append(", ");
            }
        }
        acceptableProjectsStr.append("]");
        
        return String.format("Student [ID: %s, Nume: %s, Proiect assignat: %s, Proiecte acceptate: %s]",
                studentId, name, assignedProject != null ? assignedProject.getProjectId() : "None", acceptableProjectsStr.toString());
    }
}
