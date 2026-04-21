import java.io.*;
import java.util.*;

public class TextEditorUndoRedo {

    static Stack<String> undoStack = new Stack<>();
    static Stack<String> redoStack = new Stack<>();
    static String currentText = "";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== TEXT EDITOR =====");
            System.out.println("1. Type Text");
            System.out.println("2. Delete Last Characters");
            System.out.println("3. Undo");
            System.out.println("4. Redo");
            System.out.println("5. Show Text");
            System.out.println("6. Find Word");
            System.out.println("7. Replace Word");
            System.out.println("8. Save to File");
            System.out.println("9. Load from File");
            System.out.println("10. Clear Text");
            System.out.println("11. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: typeText(); break;
                case 2: deleteText(); break;
                case 3: undo(); break;
                case 4: redo(); break;
                case 5: showText(); break;
                case 6: findWord(); break;
                case 7: replaceWord(); break;
                case 8: saveToFile(); break;
                case 9: loadFromFile(); break;
                case 10: clearText(); break;
                case 11: return;

                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ===== TYPE =====
    static void typeText() {
        System.out.print("Enter text: ");
        String text = sc.nextLine();

        undoStack.push(currentText);
        currentText += text;

        redoStack.clear();
    }

    // ===== DELETE =====
    static void deleteText() {
        if (currentText.isEmpty()) {
            System.out.println("Nothing to delete!");
            return;
        }

        System.out.print("Enter number of characters to delete: ");
        int n = sc.nextInt();

        undoStack.push(currentText);

        if (n >= currentText.length()) {
            currentText = "";
        } else {
            currentText = currentText.substring(0, currentText.length() - n);
        }

        redoStack.clear();
    }

    // ===== UNDO =====
    static void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo!");
            return;
        }

        redoStack.push(currentText);
        currentText = undoStack.pop();
    }

    // ===== REDO =====
    static void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo!");
            return;
        }

        undoStack.push(currentText);
        currentText = redoStack.pop();
    }

    // ===== SHOW =====
    static void showText() {
        System.out.println("Current Text: " + currentText);
    }

    // ===== FIND =====
    static void findWord() {
        System.out.print("Enter word to find: ");
        String word = sc.nextLine();

        if (currentText.contains(word)) {
            System.out.println("✅ Word found!");
        } else {
            System.out.println("❌ Word not found!");
        }
    }

    // ===== REPLACE =====
    static void replaceWord() {
        System.out.print("Enter word to replace: ");
        String oldWord = sc.nextLine();

        System.out.print("Enter new word: ");
        String newWord = sc.nextLine();

        undoStack.push(currentText);

        currentText = currentText.replace(oldWord, newWord);

        redoStack.clear();
    }

    // ===== CLEAR =====
    static void clearText() {
        undoStack.push(currentText);
        currentText = "";
        redoStack.clear();
        System.out.println("Text cleared!");
    }

    // ===== SAVE =====
    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter("text.txt");
            fw.write(currentText);
            fw.close();
            System.out.println("Saved to file!");
        } catch (Exception e) {
            System.out.println("Error saving!");
        }
    }

    // ===== LOAD =====
    static void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("text.txt"));
            undoStack.push(currentText);

            currentText = "";
            String line;

            while ((line = br.readLine()) != null) {
                currentText += line + "\n";
            }

            br.close();
            System.out.println("Loaded from file!");
        } catch (Exception e) {
            System.out.println("Error loading!");
        }
    }
}
