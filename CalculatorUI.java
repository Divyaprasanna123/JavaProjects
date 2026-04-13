import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Apex Scientific - A premium scientific calculator with integrated history.
 */
public class CalculatorUI extends JFrame {
    private final JTextField display;
    private final HistoryManager historyManager;
    private final DefaultListModel<String> historyListModel;
    private final JList<String> historyList;
    
    private boolean isResultDisplayed = false;
    private double memory = 0;
    
    // Components for toggle
    private JPanel scientificPanel;
    private boolean isScientificMode = true;

    // Theme Colors
    private static final Color BG_COLOR = new Color(24, 24, 24);
    private static final Color DISPLAY_BG = new Color(30, 30, 30);
    private static final Color BUTTON_BG = new Color(45, 45, 45);
    private static final Color OP_BUTTON_BG = new Color(0, 102, 204);
    private static final Color ACCENT_COLOR = new Color(180, 50, 50);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Font DISPLAY_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font SIDEBAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public CalculatorUI() {
        this.historyManager = new HistoryManager();
        this.historyListModel = new DefaultListModel<>();
        this.historyList = new JList<>(historyListModel);
        
        setTitle("Apex Scientific");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BG_COLOR);

        // Set Window Icon
        try {
            setIconImage(new ImageIcon("icon.png").getImage());
        } catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
        }

        // --- TOP SECTION: BRANDING & DISPLAY ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_COLOR);

        JPanel brandArea = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        brandArea.setBackground(BG_COLOR);
        
        // Add Icon to Header
        try {
            Image img = new ImageIcon("icon.png").getImage();
            Image scaledImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImg));
            brandArea.add(iconLabel);
        } catch (Exception e) {}

        JLabel brandLabel = new JLabel("APEX SCIENTIFIC");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        brandLabel.setForeground(new Color(212, 175, 55)); // Metallic Gold
        brandArea.add(brandLabel);
        
        topPanel.add(brandArea, BorderLayout.NORTH);

        display = new JTextField();
        display.setEditable(false);
        display.setBackground(DISPLAY_BG);
        display.setForeground(TEXT_COLOR);
        display.setFont(DISPLAY_FONT);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        topPanel.add(display, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        // --- CENTER SECTION: CALCULATOR ---
        JPanel calcContainer = new JPanel(new BorderLayout(0, 0));
        calcContainer.setBackground(BG_COLOR);

        // Control Bar (Memory, Mode, etc)
        JPanel controlBar = new JPanel(new GridLayout(1, 5, 1, 1));
        controlBar.setBackground(BG_COLOR);
        String[] controls = {"M+", "MR", "MC", "Mode", "Exit"};
        for (String ctrl : controls) {
            controlBar.add(createButton(ctrl, new Color(60, 60, 60)));
        }
        calcContainer.add(controlBar, BorderLayout.NORTH);

        // Button Area
        JPanel buttonsArea = new JPanel(new GridLayout(1, 2, 1, 1));
        buttonsArea.setBackground(BG_COLOR);

        // Scientific Panel
        scientificPanel = new JPanel(new GridLayout(6, 2, 1, 1));
        scientificPanel.setBackground(BG_COLOR);
        String[] sciButtons = {
            "sin", "cos", "tan", "sqrt", 
            "log", "ln", "pi", "e",
            " ^ ", " ( ", " ) ", "%"
        };
        for (String text : sciButtons) {
            scientificPanel.add(createButton(text, new Color(40, 40, 40)));
        }
        buttonsArea.add(scientificPanel);

        // Basic Panel
        JPanel basicPanel = new JPanel(new GridLayout(6, 4, 1, 1));
        basicPanel.setBackground(BG_COLOR);
        String[] basicButtons = {
            "C", "Back", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "0",
            ".", "=", " ", " "
        };
        for (String text : basicButtons) {
            if (text.trim().isEmpty()) {
                basicPanel.add(new JLabel(""));
            } else {
                basicPanel.add(createButton(text, BUTTON_BG));
            }
        }
        buttonsArea.add(basicPanel);
        calcContainer.add(buttonsArea, BorderLayout.CENTER);
        
        add(calcContainer, BorderLayout.CENTER);

        // --- EAST SECTION: HISTORY SIDEBAR ---
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(DISPLAY_BG);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.DARK_GRAY));

        JLabel historyHeader = new JLabel("HISTORY", SwingConstants.CENTER);
        historyHeader.setForeground(Color.GRAY);
        historyHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        historyHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        sidebar.add(historyHeader, BorderLayout.NORTH);

        historyList.setBackground(DISPLAY_BG);
        historyList.setForeground(TEXT_COLOR);
        historyList.setFont(SIDEBAR_FONT);
        historyList.setFixedCellHeight(30);
        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setBorder(null);
        sidebar.add(scrollPane, BorderLayout.CENTER);

        JButton clearHistBtn = new JButton("Clear History");
        clearHistBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clearHistBtn.setBackground(DISPLAY_BG);
        clearHistBtn.setForeground(Color.LIGHT_GRAY);
        clearHistBtn.setFocusPainted(false);
        clearHistBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        clearHistBtn.addActionListener(e -> {
            historyManager.clearHistory();
            historyListModel.clear();
        });
        sidebar.add(clearHistBtn, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.EAST);

        // Keyboard Support
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        setFocusable(true);

        setLocationRelativeTo(null);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(BUTTON_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(TEXT_COLOR);
        btn.setBackground(bg);

        // Special styling
        if (text.equals("=") || text.equals("Mode")) {
            btn.setBackground(OP_BUTTON_BG);
        } else if (text.equals("C") || text.equals("Back")) {
            btn.setBackground(ACCENT_COLOR);
        }

        btn.addActionListener(e -> {
            handleButtonClick(text);
            this.requestFocus();
        });
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(btn.getBackground().brighter());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(btn.getBackground().darker());
            }
        });

        return btn;
    }

    private void handleButtonClick(String text) {
        if (isResultDisplayed && !"=+-*/%^".contains(text.trim()) && !isMemoryCommand(text)) {
            display.setText("");
            isResultDisplayed = false;
        }

        switch (text) {
            case "=":
                evaluate();
                break;
            case "C":
                display.setText("");
                break;
            case "Back":
                String current = display.getText();
                if (!current.isEmpty()) {
                    display.setText(current.substring(0, current.length() - 1));
                }
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Mode":
                toggleScientific();
                break;
            case "M+":
                try {
                    memory += Double.parseDouble(display.getText());
                    isResultDisplayed = true;
                } catch (Exception e) {}
                break;
            case "MR":
                display.setText(display.getText() + (memory == (long)memory ? String.valueOf((long)memory) : String.valueOf(memory)));
                break;
            case "MC":
                memory = 0;
                break;
            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
            case "sqrt":
                display.setText(display.getText() + text + "(");
                break;
            default:
                display.setText(display.getText() + text.trim());
                break;
        }
    }

    private boolean isMemoryCommand(String text) {
        return text.equals("M+") || text.equals("MR") || text.equals("MC");
    }

    private void toggleScientific() {
        isScientificMode = !isScientificMode;
        scientificPanel.setVisible(isScientificMode);
        revalidate();
        repaint();
    }

    private void evaluate() {
        String expression = display.getText();
        try {
            String result = CalculatorLogic.evaluate(expression);
            historyManager.addEntry(expression, result);
            updateHistoryView(expression + " = " + result);
            display.setText(result);
            isResultDisplayed = true;
        } catch (Exception e) {
            display.setText("Error");
            isResultDisplayed = true;
        }
    }

    private void updateHistoryView(String entry) {
        historyListModel.addElement(entry);
        // Scroll to bottom
        int lastIndex = historyListModel.getSize() - 1;
        if (lastIndex >= 0) {
            historyList.ensureIndexIsVisible(lastIndex);
        }
    }

    private void handleKeyEvent(KeyEvent e) {
        char keyChar = e.getKeyChar();
        int keyCode = e.getKeyCode();

        if (Character.isDigit(keyChar) || "+-*/%^.()".indexOf(keyChar) != -1) {
            handleButtonClick(String.valueOf(keyChar));
        } else if (keyCode == KeyEvent.VK_ENTER) {
            handleButtonClick("=");
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            handleButtonClick("Back");
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            handleButtonClick("C");
        }
    }
}
