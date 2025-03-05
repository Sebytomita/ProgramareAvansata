/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package lab2;

/**
 * @author Seby
 */

public class Project {
    private String projectId; 
    private String title; 
    private ProjectType type; 
    private Student assignedStudent;

    public Project(String projectId, String title, ProjectType type) {
        this.projectId = projectId;
        this.title = title;
        this.type = type;
        this.assignedStudent = null;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public Student getAssignedStudent() {
        return assignedStudent;
    }

    public void setAssignedStudent(Student assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    @Override
    public String toString() {
        return String.format("Proiect [ID: %s, Titlu: %s (%s), Student assignat: %s]",
                projectId, title, type, assignedStudent != null ? assignedStudent.getStudentId() : "None");
    }
}
