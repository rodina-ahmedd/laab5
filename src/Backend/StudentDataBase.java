package Backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class StudentDataBase {

    private static final String FILENAME = "student.txt";

    private ArrayList<Student> students;

    public StudentDataBase() throws IOException {
        this.students = loadStudentsFromFile();
    }

    public boolean addStudent(Student student) throws IOException {
        ArrayList<Student> students = loadStudentsFromFile();

        for (Student existingStudent : students) {
            if (existingStudent.getID() == student.getID()) {
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(student.Save());
            writer.newLine();
            this.students.add(student);
            return true;
        }
    }

    public ArrayList<Student> loadStudentsFromFile() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(FILENAME);

        if (!file.exists()) {
            return students;
        }

        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {

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

                        Student student = new Student(id, name, age, gender, DP, gpa);
                        students.add(student);

                    } catch (NumberFormatException e) {
                        System.err.println("error in student data" + line);
                    } catch (IllegalArgumentException e) {

                        System.err.println("error in student data" + e.getMessage() + " | Line: " + line);
                    }
                } else {

                    System.err.println("You must fill all fields " + line);
                }
            }
        }
        return students;
    }

    public void viewStudent() {
        if (students.isEmpty()) {
            System.out.print("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student.toTableFormat());
        }
    }

    public void updateStudent(int id, String name, int age, String DP, double gpa) throws IOException {
        Student found = null;
        for (Student std : students) {

            if (std.getID() == id) {
                found = std;
                break;
            }

        }

        if (found == null) {
            System.out.print("NO STUDENT WITH SUCH ID IS FOUND");
        } else {
            found.setName(name);
            found.setAge(age);
            found.setDepartment(DP);
            found.setGPA(gpa);

            saveStudentsToFile(FILENAME);
            System.out.print("Student updated successfully");
        }
    }

    public void DeleteStudent(int id) throws IOException {
        Student found = null;
        for (Student std : students) {

            if (std.getID() == id) {
                found = std;
                break;
            }
        }

        if (found == null) {
            System.out.print("NO STUDENT WITH SUCH ID IS FOUND");
        } else {
            students.remove(found);

            saveStudentsToFile(FILENAME);
            System.out.print("Student removed successfully");
        }

    }

    public ArrayList<Student> search(String key) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student stu : students) {
            if (key.equalsIgnoreCase(stu.getName())) {
                result.add(stu);
            } else {
                try {
                    if (Integer.parseInt(key) == stu.getID()) {
                        result.add(stu);
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        return result;
    }

    public void DisplaySearchresults(String key) {
        ArrayList<Student> result = search(key);
        if (result.isEmpty()) {
            System.out.print("No found student with search key : " + key);
            return;
        } else {
            for (Student student : result) {
                System.out.println(student.toTableFormat());
            }
            System.out.println("Found: " + result.size() + " students");

        }

    }

    private void saveStudentsToFile(String FILENAME) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {

            for (Student st : students) {
                bw.write(st.Save());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.print("ERROR IN SAVING FILES");
        }
    }

    public Student getById(int id) {
        for (Student s : students) {
            if (s.getID() == id) {
                return s;
            }
        }
        return null;

    }

    public Student getByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;

    }

    public int generateId() {
        Random random = new Random();
        int id;

        while (true) {
            id = 1000 + random.nextInt(9000);

            boolean found = false;
            for (Student s : students) {
                if (s.getID() == id) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return id;
            }
        }
    }
}
