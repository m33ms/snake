import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    //coordinates of the snake on the board
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    //Right(R), Left(L), Up(U), Down(D)
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        //sets dimension for game
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //drawing grid for board
        for(int i=0; i<SCREEN_HEIGHT; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            g.setColor(Color.gray);
        }
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        //drawing body of snake
        for(int i=0; i<bodyParts; i++) {
            //head
            if (i==0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    //generates coordinates of new apple when needed
    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() {
        for(int i = bodyParts; i>0; i--) {
            //shifting body parts of snake around
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // y[0] = head of snake
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {

    }

    public void checkCollisions() {
        //check if head collides  with body
        for(int i=bodyParts; i>0; i--) {
            if((x[0]==x[i]) && (y[0] == y[i])) {
                //game over
                running = false;
            }
        }
        //if head touches left border
        if(x[0]<0) {
            running = false;
        }
        //if head touches right border
        if(x[0]>SCREEN_WIDTH) {
            running = false;
        }
        //if head touches top border
        if(y[0]<0) {
            running = false;
        }
        //if head touches bottom border
        if(y[0]>SCREEN_HEIGHT) {
            running = false;
        }

        //stop timer when game over
        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //game running
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }

        //not running
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }

    }
}
