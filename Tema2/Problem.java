/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tema2;

/**
 * @author Seby
 */
public class Problem {
    private Student[] students;
    private Teacher[] teachers;
    private Project[] projects;

    public Problem(Student[] students, Teacher[] teachers, Project[] projects) {
        this.students = students != null ? students.clone() : new Student[0];
        this.teachers = teachers != null ? teachers.clone() : new Teacher[0];
        this.projects = projects != null ? projects.clone() : new Project[0];
    }

    public Person[] getAllPersons() {
        Person[] result = new Person[students.length + teachers.length];
        System.arraycopy(students, 0, result, 0, students.length);
        System.arraycopy(teachers, 0, result, students.length, teachers.length);
        return result;
    }

    public void solveGreedy() {
        boolean[] projectAssigned = new boolean[projects.length];
        
        for (Student student : students) {
            if (student.getAssignedProject() != null) continue;
            
            Project[] acceptable = student.getAcceptableProjects();
            for (Project pref : acceptable) {
                int projectIndex = -1;
                for (int i = 0; i < projects.length; i++) {
                    if (projects[i].equals(pref)) {
                        projectIndex = i;
                        break;
                    }
                }
                
                if (projectIndex != -1 && !projectAssigned[projectIndex] && projects[projectIndex].getAssignedStudent() == null) {
                    student.setAssignedProject(projects[projectIndex]);
                    projects[projectIndex].setAssignedStudent(student);
                    projectAssigned[projectIndex] = true;
                    break;
                }
            }
        }
    }
}
