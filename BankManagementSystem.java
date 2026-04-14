import java.io.*;
import java.util.*;

// Account class
class Account implements Serializable {
    int accNo;
    String name;
    double balance;
    int pin;
    ArrayList<String> history;

    Account(int accNo, String name, double balance, int pin) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
        this.history = new ArrayList<>();
    }
}

public class BankManagementSystem {

    static ArrayList<Account> accounts = new ArrayList<>();
    static final String FILE_NAME = "accounts.dat";

    public static void main(String[] args) {

        loadData(); // 🔥 load previous data

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch(choice) {

                case 1:
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double bal = sc.nextDouble();

                    System.out.print("Set 4-digit PIN: ");
                    int pin = sc.nextInt();

                    Account newAcc = new Account(accNo, name, bal, pin);
                    newAcc.history.add("Account created with balance " + bal);

                    accounts.add(newAcc);
                    saveData();

                    System.out.println("✅ Account Created!");
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    Account acc = findAccount(accNo);
                    if(acc != null) {
                        System.out.print("Enter PIN: ");
                        int enteredPin = sc.nextInt();

                        if(acc.pin == enteredPin) {
                            userMenu(sc, acc);
                        } else {
                            System.out.println("❌ Wrong PIN!");
                        }
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 🔐 Logged-in menu
    static void userMenu(Scanner sc, Account acc) {
        while(true) {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch(choice) {

                case 1:
                    System.out.print("Enter amount: ");
                    double dep = sc.nextDouble();

                    if(dep > 0) {
                        acc.balance += dep;
                        acc.history.add("Deposited: " + dep);
                        saveData();
                        System.out.println("💰 Deposited!");
                    }
                    break;

                case 2:
                    System.out.print("Enter amount: ");
                    double wit = sc.nextDouble();

                    if(wit > 0 && acc.balance >= wit) {
                        acc.balance -= wit;
                        acc.history.add("Withdrawn: " + wit);
                        saveData();
                        System.out.println("💸 Withdrawn!");
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                    break;

                case 3:
                    System.out.println("Balance: " + acc.balance);
                    break;

                case 4:
                    System.out.println("📜 Transaction History:");
                    for(String h : acc.history) {
                        System.out.println(h);
                    }
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 🔍 Find account
    static Account findAccount(int accNo) {
        for(Account acc : accounts) {
            if(acc.accNo == accNo) {
                return acc;
            }
        }
        return null;
    }

    // 💾 Save data
    static void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(accounts);
            oos.close();
        } catch(Exception e) {
            System.out.println("Error saving data");
        }
    }

    // 📂 Load data
    static void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            accounts = (ArrayList<Account>) ois.readObject();
            ois.close();
        } catch(Exception e) {
            // first time no file
        }
    }
}