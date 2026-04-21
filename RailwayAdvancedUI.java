import java.util.*;

class Train {
    String name;
    int totalSeats = 50;
    int bookedSeats = 0;

    Train(String name) {
        this.name = name;
    }
}

class Ticket {
    String id;
    String details;

    Ticket(String id, String details) {
        this.id = id;
        this.details = details;
    }
}

public class RailwayAdvancedUI {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Ticket> tickets = new ArrayList<>();
    static int ticketCounter = 1;
    static int seatNo = 1;

    // Train database
    static ArrayList<Train> trains = new ArrayList<>();

    public static void main(String[] args) {

        // Sample trains
        trains.add(new Train("Chennai Express"));
        trains.add(new Train("Hyderabad Superfast"));
        trains.add(new Train("Bangalore Mail"));

        while (true) {

            System.out.println("\n===== 🚆 SMART RAIL SYSTEM =====");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. View Seats");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    bookTicket();
                    break;

                case 2:
                    cancelTicket();
                    break;

                case 3:
                    viewSeats();
                    break;

                case 4:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ===== BOOK =====
    static void bookTicket() {

        sc.nextLine();

        System.out.print("From: ");
        String from = sc.nextLine();

        System.out.print("To: ");
        String to = sc.nextLine();

        System.out.print("Date: ");
        String date = sc.nextLine();

        // Show trains
        System.out.println("\nAvailable Trains:");
        for (int i = 0; i < trains.size(); i++) {
            System.out.println((i + 1) + ". " + trains.get(i).name +
                    " (Seats Left: " + (trains.get(i).totalSeats - trains.get(i).bookedSeats) + ")");
        }

        System.out.print("Select train: ");
        int choice = sc.nextInt();
        Train selectedTrain = trains.get(choice - 1);

        System.out.print("Number of passengers (max 5): ");
        int n = sc.nextInt();

        if (n > 5 || n <= 0) {
            System.out.println("Invalid number!");
            return;
        }

        if (selectedTrain.bookedSeats + n > selectedTrain.totalSeats) {
            System.out.println("❌ Not enough seats!");
            return;
        }

        String id = "T" + ticketCounter++;
        String result = "\n🎫 Ticket ID: " + id + "\n";
        result += "Train: " + selectedTrain.name + "\n";
        result += "From: " + from + " → " + to + " | Date: " + date + "\n\n";

        for (int i = 1; i <= n; i++) {

            sc.nextLine();

            System.out.println("\nPassenger " + i);

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Physically Challenged? (1/0): ");
            int pc = sc.nextInt();

            System.out.print("Senior Citizen? (1/0): ");
            int senior = sc.nextInt();

            String berth;

            if (pc == 1)
                berth = "Lower (PC)";
            else if (senior == 1 || age >= 45)
                berth = "Lower";
            else if (age > 30)
                berth = "Middle";
            else
                berth = "Upper";

            result += "👤 " + name +
                      " | Age: " + age +
                      " | " + berth +
                      " | Seat: S1-" + seatNo + "\n";

            seatNo++;
            selectedTrain.bookedSeats++;
        }

        tickets.add(new Ticket(id, result));

        System.out.println("\n===== BOOKED SUCCESSFULLY =====");
        System.out.println(result);
    }

    // ===== CANCEL =====
    static void cancelTicket() {

        System.out.print("Enter Ticket ID: ");
        String id = sc.next();

        boolean found = false;

        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).id.equals(id)) {
                tickets.remove(i);
                found = true;
                break;
            }
        }

        if (found)
            System.out.println("✅ Cancelled!");
        else
            System.out.println("❌ Not Found!");
    }

    // ===== VIEW SEATS =====
    static void viewSeats() {

        System.out.println("\n===== SEAT AVAILABILITY =====");

        for (Train t : trains) {
            System.out.println(t.name +
                    " → Available Seats: " + (t.totalSeats - t.bookedSeats));
        }
    }
}
