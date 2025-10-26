/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author mo
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; // Was missing
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Student {
    private static final String FILENAME = "student.txt";

    public static boolean addStudent(StudentDataBase student) throws IOException {
        ArrayList<StudentDataBase> students = loadStudentsFromFile();
        
        for (StudentDataBase existingStudent : students) {
            if (existingStudent.getID() == student.getID()) {
                return false; // Return false to indicate student was not added
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(student.Save());
            writer.newLine();
            return true; // Return true to indicate success
        } 
    }
    public static ArrayList<StudentDataBase> loadStudentsFromFile() throws IOException {
        ArrayList<StudentDataBase> students = new ArrayList<>();
        File file = new File(FILENAME);
        
        if (!file.exists()) {
            return students;
        }
        
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) { // Skip empty lines
                    continue;
                }
        String[] parts = line.split(",");
                
                if (parts.length == 6) {
                    try {
                        
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        int age = Integer.parseInt(parts[2].trim());
                        String gender = parts[3].trim();
                        String DP = parts[4].trim();
                        double gpa = Double.parseDouble(parts[5].trim());

                        StudentDataBase student = new StudentDataBase(id, name, age, gender, DP, gpa);
                        students.add(student);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("error in student data" + line);
                        }
                    catch (IllegalArgumentException e) {
                        // Handle data that violates validation rules (e.g., age = 99)
                        System.err.println("error in student data" + e.getMessage() + " | Line: " + line);
                    }
                }
                    else {
                    
                     System.err.println("You must fill all fields " + line);
                }
                }
            }
            return students;
    }
}

