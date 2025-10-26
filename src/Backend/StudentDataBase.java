
package Backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
                return false;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(student.Save());
            writer.newLine();
            return true; 
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
                if (line.trim().isEmpty()) { 
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
    
     public void viewStudent()
    {
        if(students.isEmpty())
        {
            System.out.print("No students found.");
            return;
        }
        for (StudentDataBase student:students)
        {
            System.out.println(student.toTableFormat());
        }
        
        public void updateStudent(int id , String name , int age , String DP , double gpa) throws IOException
    {
        StudentDataBase found = null ; 
        for (StudentDataBase std : students)
        {
            
         if(std.getID()==id){
             found = std ; 
             break ;
         }
         
        }
        
        if(found==null)
            System.out.print("NO STUDENT WITH SUCH ID IS FOUND");
        else {
            found.setName(name);
            found.setAge(age);
            found.setDepartment(DP);
            found.setGPA(gpa);
    
        saveStudentsToFile(FILENAME);
        System.out.print("Student updated successfully");
        }
        
        public void DeleteStudent(int id) throws IOException
    {
        StudentDataBase found = null ;
         for (StudentDataBase std : students)
        {
            
         if(std.getID()==id){
             found = std ; 
             break ;
         }
        }
         
         if(found==null)
            System.out.print("NO STUDENT WITH SUCH ID IS FOUND");
        else {
            students.remove(found);
    
        saveStudentsToFile(FILENAME);
        System.out.print("Student removed successfully");
        }
         
    }
        
        public ArrayList<StudentDataBase> search(String key)
    {
     ArrayList<StudentDataBase> result = new ArrayList<>(); 
     
     for (StudentDataBase stu : students)
     {
         if(key.equals(stu.getName())||Integer.parseInt(key)==stu.getID())
         {
             result.add(stu);
         }
     }
     
     return result ; 
    }
    
    public void DisplaySearchresults(String key)
    {
        ArrayList<StudentDataBase> result = search (key);
        if(result.isEmpty()){
            System.out.print("No found student with search key : "+ key);
            return;
                    }
        else {
             for (StudentDataBase student : result) {
            System.out.println(student.toTableFormat());
        }
        System.out.println("Found: " + result.size() + " students");
        
        }
        
        
    }
 
    private void saveStudentsToFile(String FILENAME) throws IOException
    {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))){
            
           for(StudentDataBase st : students)
           {
               bw.write(st.Save());
               bw.newLine();
           }
            
        }
        
        catch(IOException e){
            System.out.print("ERROR IN SAVING FILES");
        }
         }
    



        
    }


    
    


