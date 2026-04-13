import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Apex Scientific application.
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to System default or cross-platform
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            CalculatorUI ui = new CalculatorUI();
            ui.setVisible(true);
        });
    }
}
