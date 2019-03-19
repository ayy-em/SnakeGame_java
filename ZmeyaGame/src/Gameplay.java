import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    
    //arrays to store positions of head & tail
    private int[] snakeXlen = new int[750];
    private int[] snakeYlen = new int[750];
    
    //booleans to signal snake direction
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    
    //icons for food, mouth(x4), tail and game logo up top
    private ImageIcon food;
    private ImageIcon rightmouth;
    private ImageIcon leftmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    private ImageIcon snakeIcon;
    private ImageIcon imgIcon;
    
    //variables reassigned after moving
    private int moves = 0;
    private int lengthOfSnake = 3;
    
    //food possible positions
    private int[] foodX = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
            525,550,575,600,625,650,675,700,725,800,825,850};
    private int[] foodY = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,
            525,550,575,600,625};
    //food position
    private Random random = new Random();
    private int foodXpos = random.nextInt(33);
    private int foodYpos = random.nextInt(22);
    //game pace
    private Timer timer;
    private int delay = 100;
    //score
    private int score = 0;
    
    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }
        
    public void paintComponent(Graphics g) {
        //snaek starting position
        if (moves == 0) {
            snakeXlen[2] = 50;
            snakeXlen[1] = 75;
            snakeXlen[0] = 100;
            //ayy
            snakeYlen[2] = 100;
            snakeYlen[1] = 100;
            snakeYlen[0] = 100;
        }
        
        //------------------GAME FIELD-------------------
        //draw title image border
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        
        //draw the title image
        imgIcon = new ImageIcon("resources/snaketitle.jpg");
        imgIcon.paintIcon(this,g, 25,11);
        
        //draw game area
        g.setColor(Color.white);
        g.drawRect(24,74,851,577);
        
        //draw background
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);
        
        //draw scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,14));
        g.drawString("Your Score: " + score, 730, 32);
        //draw length
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,14));
        g.drawString("Snake Length: " + lengthOfSnake, 730, 52);
        
        //-----------------MOVING STUFF------------------
        //snake mouth
        rightmouth = new ImageIcon("resources/rightmouth.png");
        rightmouth.paintIcon(this, g, snakeXlen[0],snakeYlen[0]);
        
        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0 && right) {
                rightmouth = new ImageIcon("resources/rightmouth.png");
                rightmouth.paintIcon(this,g,snakeXlen[a],snakeYlen[a]);
            }
            if (a == 0 && left) {
                leftmouth = new ImageIcon("resources/leftmouth.png");
                leftmouth.paintIcon(this,g,snakeXlen[a],snakeYlen[a]);
            }
            if (a == 0 && up) {
                upmouth = new ImageIcon("resources/upmouth.png");
                upmouth.paintIcon(this,g,snakeXlen[a],snakeYlen[a]);
            }
            if (a == 0 && down) {
                downmouth = new ImageIcon("resources/downmouth.png");
                downmouth.paintIcon(this,g,snakeXlen[a],snakeYlen[a]);
            }
            if (a != 0) {
                snakeIcon = new ImageIcon("resources/snakeimage.png");
                snakeIcon.paintIcon(this,g,snakeXlen[a],snakeYlen[a]);
            }
        }
        
        //collision detection
        food = new ImageIcon("resources/food.png");
        if (foodX[foodXpos] == snakeXlen[0] && foodY[foodYpos] == snakeYlen[0]) {
            lengthOfSnake++;
            //generate random position of food,  check collision food and snake head
            boolean foodPositionInsideSnake = true;
            while (foodPositionInsideSnake) {
                foodXpos = random.nextInt(34);
                foodYpos = random.nextInt(23);
                foodPositionInsideSnake = foodXpos == snakeXlen[0] && foodYpos == snakeYlen[0];
            }
            if (delay > 10) {
                delay = delay - 5;
                timer.setDelay(delay);
            }
            score = score + 5;
        }
        food.paintIcon(this,g,foodX[foodXpos],foodY[foodYpos]);
        
        for (int h = 1; h < lengthOfSnake; h++) {
            if (snakeXlen[h] == snakeXlen[0] && snakeYlen[h] == snakeYlen[0]) {
                ShowGameOverScreen(g);
            }
        }
    
        g.dispose();
    }
    
    
    private void ShowGameOverScreen(Graphics g) {
        right = left = up = down = false;
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.drawString("Game Over", 300,300);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Press SPACE to restart", 320,340);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            StartNewGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (!left) {
                moves++;
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (!right) {
                moves++;
                left = true;
            } else {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (!down) {
                moves++;
                up = true;
            } else {
                up = false;
                down = true;
            }
            left = false;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!up) {
                moves++;
                down = true;
            } else {
                down = false;
                up = true;
            }
            left = false;
            right = false;
        }
    }
    
    private void StartNewGame() {
        moves = 0;
        score = 0;
        lengthOfSnake = 3;
        delay = 100;
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        //nothing
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        //-------------------------------------------------
        if (right) {
            for (int k = lengthOfSnake - 1; k >= 0; k--) {
                snakeYlen[k+1] = snakeYlen [k];
            }
            for (int l = lengthOfSnake; l >= 0; l--) {
                if (l == 0) {
                    snakeXlen[l] = snakeXlen[l] + 25;
                } else {
                    snakeXlen[l] = snakeXlen[l-1];
                }
                if (snakeXlen[l] > 850) {
                    snakeXlen[l] = 25;
                }
            }
            repaint();
        }
        //-------------------------------------------------
        if (left) {
            for (int k = lengthOfSnake - 1; k >= 0; k--) {
                snakeYlen[k+1] = snakeYlen [k];
            }
            for (int l = lengthOfSnake; l >= 0; l--) {
                if (l == 0) {
                    snakeXlen[l] = snakeXlen[l] - 25;
                } else {
                    snakeXlen[l] = snakeXlen[l-1];
                }
                if (snakeXlen[l] < 25) {
                    snakeXlen[l] = 850;
                }
            }
            repaint();
        }
        //-------------------------------------------------
        if (down) {
            for (int k = lengthOfSnake - 1; k >= 0; k--) {
                snakeXlen[k+1] = snakeXlen [k];
            }
            for (int l = lengthOfSnake; l >= 0; l--) {
                if (l == 0) {
                    snakeYlen[l] = snakeYlen[l] + 25;
                } else {
                    snakeYlen[l] = snakeYlen[l-1];
                }
                if (snakeYlen[l] > 625) {
                    snakeYlen[l] = 75;
                }
            }
            repaint();
        }
        //-------------------------------------------------
        if (up) {
            for (int k = lengthOfSnake - 1; k >= 0; k--) {
                snakeXlen[k+1] = snakeXlen [k];
            }
            for (int l = lengthOfSnake; l >= 0; l--) {
                if (l == 0) {
                    snakeYlen[l] = snakeYlen[l] - 25;
                } else {
                    snakeYlen[l] = snakeYlen[l-1];
                }
                if (snakeYlen[l] < 75) {
                    snakeYlen[l] = 625;
                }
            }
            repaint();
        }
    }
}
