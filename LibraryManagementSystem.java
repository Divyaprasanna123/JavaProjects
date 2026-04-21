import java.util.*;

// Book class
class Book {
    int id;
    String title;
    String author;
    boolean isIssued;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }
}

public class LibraryManagementSystem {

    static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch(choice) {

                // ADD BOOK
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();

                    books.add(new Book(id, title, author));
                    System.out.println("📚 Book added!");
                    break;

                // VIEW BOOKS
                case 2:
                    if(books.isEmpty()) {
                        System.out.println("No books available!");
                    } else {
                        for(Book b : books) {
                            System.out.println("ID: " + b.id +
                                               ", Title: " + b.title +
                                               ", Author: " + b.author +
                                               ", Status: " + (b.isIssued ? "Issued" : "Available"));
                        }
                    }
                    break;

                // ISSUE BOOK
                case 3:
                    System.out.print("Enter Book ID to issue: ");
                    int issueId = sc.nextInt();

                    Book issueBook = findBook(issueId);
                    if(issueBook != null) {
                        if(!issueBook.isIssued) {
                            issueBook.isIssued = true;
                            System.out.println("✅ Book issued!");
                        } else {
                            System.out.println("Already issued!");
                        }
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                // RETURN BOOK
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    int returnId = sc.nextInt();

                    Book returnBook = findBook(returnId);
                    if(returnBook != null) {
                        if(returnBook.isIssued) {
                            returnBook.isIssued = false;
                            System.out.println("📥 Book returned!");
                        } else {
                            System.out.println("This book was not issued!");
                        }
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                // SEARCH BOOK
                case 5:
                    System.out.print("Enter Book ID to search: ");
                    int searchId = sc.nextInt();

                    Book searchBook = findBook(searchId);
                    if(searchBook != null) {
                        System.out.println("Found → ID: " + searchBook.id +
                                           ", Title: " + searchBook.title +
                                           ", Author: " + searchBook.author +
                                           ", Status: " + (searchBook.isIssued ? "Issued" : "Available"));
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                // EXIT
                case 6:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 🔍 Find Book Method
    static Book findBook(int id) {
        for(Book b : books) {
            if(b.id == id) {
                return b;
            }
        }
        return null;
    }
}
