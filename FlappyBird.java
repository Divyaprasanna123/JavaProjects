import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int birdX = 200;
    int birdY = 300;
    int velocity = 0;
    int gravity = 1;

    int moveSpeed = 15;   // 🔥 arrow key speed
    int pipeSpeed = 4;
    int pipeWidth = 60;
    int pipeGap = 160;

    ArrayList<Integer> pipeX = new ArrayList<>();
    ArrayList<Integer> pipeHeight = new ArrayList<>();

    int score = 0;
    boolean gameOver = false;

    javax.swing.Timer timer;
    Random random;

    JButton restartButton;

    int cloudX = 0;

    FlappyBird() {
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);

        random = new Random();

        for(int i = 0; i < 4; i++) {
            pipeX.add(600 + i * 250);
            pipeHeight.add(random.nextInt(300) + 50);
        }

        restartButton = new JButton("Restart");
        restartButton.setVisible(false);

        restartButton.addActionListener(e -> {
            restartGame();
            restartButton.setVisible(false);
            this.requestFocusInWindow();
        });

        this.add(restartButton);

        timer = new javax.swing.Timer(20, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Sky
        g.setColor(new Color(135, 206, 235));
        g.fillRect(0, 0, width, height);

        // Clouds
        g.setColor(Color.WHITE);
        g.fillOval(cloudX, 50, 100, 50);
        g.fillOval(cloudX + 200, 80, 120, 60);

        // Trees
        g.setColor(new Color(34, 139, 34));
        g.fillRect(50, height - 120, 30, 60);
        g.fillOval(40, height - 150, 50, 50);

        // Plants
        g.setColor(Color.GREEN);
        g.fillOval(150, height - 70, 20, 20);
        g.fillOval(300, height - 70, 20, 20);

        // Ground
        g.setColor(new Color(222, 184, 135));
        g.fillRect(0, height - 60, width, 60);

        // Bird
        g.setColor(Color.YELLOW);
        g.fillOval(birdX, birdY, 30, 30);

        g.setColor(Color.BLACK);
        g.fillOval(birdX + 18, birdY + 8, 5, 5);

        g.setColor(Color.ORANGE);
        int[] xPoints = {birdX + 30, birdX + 40, birdX + 30};
        int[] yPoints = {birdY + 10, birdY + 15, birdY + 20};
        g.fillPolygon(xPoints, yPoints, 3);

        // Pipes
        g.setColor(Color.GREEN);
        for(int i = 0; i < pipeX.size(); i++) {
            g.fillRect(pipeX.get(i), 0, pipeWidth, pipeHeight.get(i));
            g.fillRect(pipeX.get(i), pipeHeight.get(i) + pipeGap, pipeWidth, height);
        }

        // Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Score: " + score, 20, 40);

        if(gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", width/2 - 120, height/2);

            restartButton.setBounds(width/2 - 70, height/2 + 40, 140, 40);
            restartButton.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(!gameOver) {

            // Gravity
            velocity += gravity;
            birdY += velocity;

            // Move clouds
            cloudX -= 1;
            if(cloudX < -200) cloudX = getWidth();

            for(int i = 0; i < pipeX.size(); i++) {
                pipeX.set(i, pipeX.get(i) - pipeSpeed);

                if(pipeX.get(i) < -pipeWidth) {
                    pipeX.set(i, getWidth());
                    pipeHeight.set(i, random.nextInt(300) + 50);
                    score++;
                }

                // Collision
                if(birdX + 30 > pipeX.get(i) && birdX < pipeX.get(i) + pipeWidth &&
                   (birdY < pipeHeight.get(i) || birdY + 30 > pipeHeight.get(i) + pipeGap)) {
                    gameOver = true;
                }
            }

            // Boundary check
            if(birdY < 0 || birdY > getHeight() - 60) gameOver = true;
            if(birdX < 0) birdX = 0;
            if(birdX > getWidth() - 30) birdX = getWidth() - 30;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {

        if(!gameOver) {

            switch(e.getKeyCode()) {

                case KeyEvent.VK_UP:
                    velocity = -10; // jump
                    break;

                case KeyEvent.VK_DOWN:
                    birdY += moveSpeed;
                    break;

                case KeyEvent.VK_LEFT:
                    birdX -= moveSpeed;
                    break;

                case KeyEvent.VK_RIGHT:
                    birdX += moveSpeed;
                    break;

                case KeyEvent.VK_SPACE:
                    velocity = -12; // strong jump
                    break;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE && gameOver) {
            restartGame();
        }
    }

    public void restartGame() {
        birdX = 200;
        birdY = 300;
        velocity = 0;
        score = 0;
        gameOver = false;

        pipeX.clear();
        pipeHeight.clear();

        for(int i = 0; i < 4; i++) {
            pipeX.add(getWidth() + i * 250);
            pipeHeight.add(random.nextInt(300) + 50);
        }

        restartButton.setVisible(false);
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird 🐦");
        FlappyBird game = new FlappyBird();

        frame.setSize(800, 600);
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}