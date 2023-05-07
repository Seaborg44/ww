package app.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;

    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int xP1[] = new int[GAME_UNITS];
    final int yP1[] = new int[GAME_UNITS];
    char directionP1 = 'R';
    boolean runningP1 = false;
    int bodyPartsP1 = 6;
    int applesEatenP1 = 0;
    final int xP2[] = new int[GAME_UNITS];
    final int yP2[] = new int[GAME_UNITS];
    char directionP2 = 'L';
    boolean runningP2 = false;
    int bodyPartsP2 = 6;
    int applesEatenP2 = 0;
    int appleX;
    int appleY;

    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        yP1[0] = 150;
        yP2[0] = 450;
        xP2[0] = SCREEN_WIDTH;
        startGame();
    }

    public void startGame() {
        newApple();
        runningP1 = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (runningP1) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyPartsP1; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(xP1[i], yP1[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(xP1[i], yP1[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEatenP1, (int) (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEatenP1)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }

    }

    public void move() {
        for (int i = bodyPartsP1; i > 0; i--) {
            xP1[i] = xP1[i - 1];
            yP1[i] = yP1[i - 1];
            System.out.println("position X: " + xP1[i] + " position Y: " + yP1[i]);
        }

        switch (directionP1) {
            case 'U' -> yP1[0] = yP1[0] - UNIT_SIZE;
            case 'D' -> yP1[0] = yP1[0] + UNIT_SIZE;
            case 'L' -> xP1[0] = xP1[0] - UNIT_SIZE;
            case 'R' -> xP1[0] = xP1[0] + UNIT_SIZE;
        }

    }

    public void checkApple() {
        if (xP1[0] == appleX && yP1[0] == appleY) {
            bodyPartsP1++;
            applesEatenP1++;
            newApple();
        }
    }

    public void checkCollissions() {
        for (int i = bodyPartsP1; i > 0; i--) {
            if (xP1[0] == xP1[i] && yP1[0] == yP1[i]) {
                runningP1 = false;
            }
        }
        if (xP1[0] < 0) {
            runningP1 = false;
        }
        if (xP1[0] > SCREEN_WIDTH) {
            runningP1 = false;
        }
        if (yP1[0] < 0) {
            runningP1 = false;
        }
        if (yP1[0] > SCREEN_HEIGHT) {
            runningP1 = false;
        }

        if (!runningP1) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 100));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (int) (SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 100));
        FontMetrics score = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEatenP1, (int) (SCREEN_WIDTH - score.stringWidth("Score: " + applesEatenP1)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (runningP1) {
            move();
            checkApple();
            checkCollissions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (directionP1 != 'R') {
                        directionP1 = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (directionP1 != 'L') {
                        directionP1 = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (directionP1 != 'D') {
                        directionP1 = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (directionP1 != 'U') {
                        directionP1 = 'D';
                    }
                    break;
            }
        }
    }
}
