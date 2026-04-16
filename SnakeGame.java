import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

    int delay = 150;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];

    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;

    javax.swing.Timer timer;
    Random random;

    JButton restartButton; // 🔘 button

    SnakeGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null); // 🔥 needed for button
        this.addKeyListener(new MyKeyAdapter());

        // 🔘 Restart Button setup
        restartButton = new JButton("Restart");
        restartButton.setBounds(220, 380, 150, 40);
        restartButton.setFocusable(false);
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> {
            restartGame();
            restartButton.setVisible(false);
            this.requestFocusInWindow();
        });

        this.add(restartButton);

        startGame();
    }

    public void startGame() {
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';

        newApple();
        running = true;

        timer = new javax.swing.Timer(delay, this);
        timer.start();
    }

    public void restartGame() {
        delay = 150;

        for(int i = 0; i < GAME_UNITS; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        restartButton.setVisible(false);
        startGame();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if(running) {

            // 🎮 Title
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            g.drawString("Kaatraaj 🐍", 150, 50);

            // 🍎 Apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.WHITE);
            g.fillOval(appleX + 8, appleY + 5, 5, 5);

            // 🐍 Snake
            for(int i = 0; i < bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 10, 10);

                    g.setColor(Color.BLACK);
                    g.fillOval(x[i] + 5, y[i] + 5, 5, 5);
                    g.fillOval(x[i] + 15, y[i] + 5, 5, 5);
                } else {
                    g.setColor(new Color(0, 150 + (i * 2) % 100, 0));
                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 10, 10);
                }
            }

            // Score
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            g.drawString("Score: " + applesEaten, 220, 80);

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int)(WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
            case 'L': x[0] -= UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
        }
    }

    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();

            if(delay > 50) {
                delay -= 5;
                timer.setDelay(delay);
            }
        }
    }

    public void checkCollisions() {

        for(int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if(x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 50));
        g.drawString("Game Over", 150, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.drawString("Score: " + applesEaten, 220, 300);

        restartButton.setVisible(true); // 🔘 show button
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            if(!running && e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            }

            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        SnakeGame gamePanel = new SnakeGame();

        frame.add(gamePanel);
        frame.setTitle("Kaatraaj 🐍");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
