/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Backend.Student;
import Backend.StudentDataBase;
import java.io.IOException;
import java.util.List;

public class Controller {

    private StudentDataBase Data;

    public Controller(StudentDataBase Data) {
        this.Data = Data;
    }

    public List<Student> getAllStudents() throws IOException {
         return Data.loadStudentsFromFile();
    }

    public Student searchById(int id) {
        return Data.getById(id);
    }

    public Student searchByName(String name) {
        return Data.getByName(name);
    }

    public void addStudent(String name, int age, String gender, String department, double gpa) throws IOException {
        int id = Data.generateId();
        Student s = new Student(id, name, age, gender, name, gpa);
        Data.addStudent(s);
    }

    public void updateStudent(int id, String name, int age, String gender, String department, double gpa) throws IOException {
        Data.updateStudent(id, name, age, name, gpa);
    }
    
     public void deleteStudent(int id) throws IOException {
        Data.DeleteStudent(id);
    }
}
