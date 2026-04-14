import java.util.*;

// Student class
class Student {
    int id;
    String name;
    int marks;

    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
}

public class StudentManagementSystem {

    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch(choice) {

                // ADD STUDENT
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // clear buffer

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();

                    students.add(new Student(id, name, marks));
                    System.out.println("Student added!");
                    break;

                // VIEW ALL STUDENTS
                case 2:
                    if(students.isEmpty()) {
                        System.out.println("No students found!");
                    } else {
                        for(Student s : students) {
                            System.out.println("ID: " + s.id + 
                                               ", Name: " + s.name + 
                                               ", Marks: " + s.marks);
                        }
                    }
                    break;

                // SEARCH STUDENT
                case 3:
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();

                    boolean found = false;

                    for(Student s : students) {
                        if(s.id == searchId) {
                            System.out.println("Found → ID: " + s.id + 
                                               ", Name: " + s.name + 
                                               ", Marks: " + s.marks);
                            found = true;
                            break;
                        }
                    }

                    if(!found) {
                        System.out.println("Student not found!");
                    }
                    break;

                // DELETE STUDENT
                case 4:
                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();

                    Iterator<Student> it = students.iterator();
                    boolean deleted = false;

                    while(it.hasNext()) {
                        Student s = it.next();
                        if(s.id == deleteId) {
                            it.remove();
                            System.out.println("Student deleted!");
                            deleted = true;
                            break;
                        }
                    }

                    if(!deleted) {
                        System.out.println("Student not found!");
                    }
                    break;

                // EXIT
                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}