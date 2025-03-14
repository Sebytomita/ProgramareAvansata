/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tema2;

/**
 * @author Seby
 */
public class Project {
    private String projectId;
    private String title;
    private ProjectType type;
    private Student assignedStudent;
    private Teacher proposingTeacher;

    public Project(String projectId, String title, ProjectType type, Teacher teacher) {
        this.projectId = projectId;
        this.title = title;
        this.type = type;
        this.proposingTeacher = teacher;
        this.assignedStudent = null;
    }

    public String getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public Teacher getProposingTeacher() { return proposingTeacher; }
    public Student getAssignedStudent() { return assignedStudent; }
    public void setAssignedStudent(Student student) { this.assignedStudent = student; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Project)) return false;
        Project other = (Project) obj;
        return projectId.equals(other.projectId);
    }
}
