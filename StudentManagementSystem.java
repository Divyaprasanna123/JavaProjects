import java.io.*;
import java.util.*;

// Student class
class Student implements Serializable {
    int id;
    String name;
    String studentClass;
    double attendance;
    int marks;

    Student(int id, String name, String studentClass, double attendance, int marks) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.attendance = attendance;
        this.marks = marks;
    }
}

public class StudentManagementSystem {

    static LinkedList<Student> students = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadFromFile();

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student (by ID)");
            System.out.println("4. Delete Student");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save & Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    sortStudents();
                    break;

                case 6:
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ===== ADD MULTIPLE STUDENTS =====
    static void addStudent() {

        System.out.print("How many students to add? ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 1; i <= n; i++) {

            System.out.println("\n--- Enter details for Student " + i + " ---");

            int id;

            // 🔒 Duplicate ID check
            while (true) {
                System.out.print("Enter ID: ");
                id = sc.nextInt();
                sc.nextLine();

                boolean exists = false;
                for (Student s : students) {
                    if (s.id == id) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    System.out.println("❌ ID already exists! Try again.");
                } else {
                    break;
                }
            }

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Class: ");
            String studentClass = sc.nextLine();

            System.out.print("Enter Attendance (%): ");
            double attendance = sc.nextDouble();

            System.out.print("Enter Marks: ");
            int marks = sc.nextInt();
            sc.nextLine(); // clear buffer

            students.add(new Student(id, name, studentClass, attendance, marks));

            System.out.println("✅ Student " + i + " added!");
        }
    }

    // ===== VIEW =====
    static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        for (Student s : students) {
            System.out.println("ID: " + s.id +
                    ", Name: " + s.name +
                    ", Class: " + s.studentClass +
                    ", Attendance: " + s.attendance +
                    ", Marks: " + s.marks);
        }
    }

    // ===== SEARCH =====
    static void searchStudent() {

        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();

        for (Student s : students) {
            if (s.id == id) {
                System.out.println("✅ Found:");
                System.out.println("Name: " + s.name +
                        ", Class: " + s.studentClass +
                        ", Attendance: " + s.attendance +
                        ", Marks: " + s.marks);
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    // ===== DELETE =====
    static void deleteStudent() {

        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == id) {
                it.remove();
                System.out.println("✅ Deleted!");
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    // ===== SORT =====
    static void sortStudents() {

        Collections.sort(students, (a, b) -> b.marks - a.marks);

        System.out.println("✅ Sorted by marks (Descending)");
        viewStudents();
    }

    // ===== SAVE =====
    static void saveToFile() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"));
            oos.writeObject(students);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving file!");
        }
    }

    // ===== LOAD =====
    static void loadFromFile() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
            students = (LinkedList<Student>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }
    }
}
