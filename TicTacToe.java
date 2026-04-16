import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame implements ActionListener {

    JButton buttons[][] = new JButton[3][3];
    char currentPlayer = 'X';
    JLabel statusLabel;
    JButton restartButton;

    TicTacToeGUI() {

        setTitle("Tic Tac Toe 🎮");
        setSize(600, 700); // 🔥 increased window size
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Status label
        statusLabel = new JLabel("Player X Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(Color.BLACK);
        statusLabel.setOpaque(true);
        add(statusLabel, BorderLayout.NORTH);

        // Center panel (to keep grid centered)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10)); // 🔥 more spacing
        panel.setPreferredSize(new Dimension(450, 450)); // 🔥 bigger grid
        panel.setBackground(Color.BLACK);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 70)); // 🔥 bigger X/O
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.DARK_GRAY);
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                buttons[i][j].addActionListener(this);
                panel.add(buttons[i][j]);
            }
        }

        centerPanel.add(panel);
        add(centerPanel, BorderLayout.CENTER);

        // Fancy Restart Button
        restartButton = new JButton("Restart Game 🔄");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBackground(new Color(0, 123, 255));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        restartButton.addActionListener(e -> resetGame());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(restartButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        JButton btn = (JButton) e.getSource();

        if(btn.getText().equals("")) {

            btn.setText(String.valueOf(currentPlayer));

            // 🎨 Color logic
            if(currentPlayer == 'X') {
                btn.setForeground(Color.GREEN);
            } else {
                btn.setForeground(Color.RED);
            }

            if(checkWinner()) {
                statusLabel.setText("🎉 Player " + currentPlayer + " Wins!");
                disableButtons();
                return;
            }

            if(isDraw()) {
                statusLabel.setText("It's a DRAW!");
                return;
            }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + " Turn");
        }
    }

    boolean checkWinner() {

        for(int i = 0; i < 3; i++) {
            if(!buttons[i][0].getText().equals("") &&
               buttons[i][0].getText().equals(buttons[i][1].getText()) &&
               buttons[i][1].getText().equals(buttons[i][2].getText())) {

                highlight(buttons[i][0], buttons[i][1], buttons[i][2]);
                return true;
            }
        }

        for(int j = 0; j < 3; j++) {
            if(!buttons[0][j].getText().equals("") &&
               buttons[0][j].getText().equals(buttons[1][j].getText()) &&
               buttons[1][j].getText().equals(buttons[2][j].getText())) {

                highlight(buttons[0][j], buttons[1][j], buttons[2][j]);
                return true;
            }
        }

        if(!buttons[0][0].getText().equals("") &&
           buttons[0][0].getText().equals(buttons[1][1].getText()) &&
           buttons[1][1].getText().equals(buttons[2][2].getText())) {

            highlight(buttons[0][0], buttons[1][1], buttons[2][2]);
            return true;
        }

        if(!buttons[0][2].getText().equals("") &&
           buttons[0][2].getText().equals(buttons[1][1].getText()) &&
           buttons[1][1].getText().equals(buttons[2][0].getText())) {

            highlight(buttons[0][2], buttons[1][1], buttons[2][0]);
            return true;
        }

        return false;
    }

    boolean isDraw() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    void highlight(JButton b1, JButton b2, JButton b3) {
        b1.setBackground(Color.GREEN);
        b2.setBackground(Color.GREEN);
        b3.setBackground(Color.GREEN);
    }

    void disableButtons() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    void resetGame() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackground(Color.DARK_GRAY);
            }
        }

        currentPlayer = 'X';
        statusLabel.setText("Player X Turn");
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
