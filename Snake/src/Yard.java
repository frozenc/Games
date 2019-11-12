import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/16 21:31
 */
public class Yard extends Frame {

    public static final int ROWS = 30;
    public static final int COLS = 30;
    public static final int BLOCK_SIZE = 15;

    private int score = 0;
    Thread thread;

    PaintThread paintThread = new PaintThread();
    private boolean gameOver = false;
    private Font fontGameOver = new Font("微软雅黑", Font.BOLD | Font.HANGING_BASELINE, 50);
    private Font fontGameStart = new Font("微软雅黑", Font.BOLD | Font.HANGING_BASELINE, 20);

    Button restart = new Button("Restart?");

    Snake s = new Snake(this);
    Egg e = new Egg();

    Image offScreenImage = null;

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
        g.setColor(c);

        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, BLOCK_SIZE * COLS, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }

        g.setColor(Color.YELLOW);
        g.drawString("score:" + score, 10, 60);
        g.setColor(Color.RED);
        if(gameOver) {
            g.setFont(fontGameOver);
            g.drawString("Game Over!", 90, 180);
            g.setFont(fontGameStart);
//            g.drawString("Restart Press Enter!!!", 90, 250);
            paintThread.gameOver();
        }

        g.setColor(c);

        s.eat(e);

        e.draw(g);
        s.draw(g);
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void init() {
        this.gameOver = true;
        this.score = 0;
        paintThread.gameStart();
        System.out.println(thread.getState());
        thread = new Thread(this.paintThread);
        thread.start();
    }

    private class PaintThread implements Runnable {
        private boolean running = true;
        @Override
        public void run() {
            while(running) {
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void gameOver() {
            running = false;
        }

        public void gameStart() {
            running = true;
        }
    }

    public void launch() {
        this.setLocation(300,30);
        this.setSize(ROWS * BLOCK_SIZE, COLS * BLOCK_SIZE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        this.setVisible(true);

        this.addKeyListener(new KeyMonitor());

//        new Thread(new PaintThread()).start();
        thread = new Thread(this.paintThread);
        thread.start();
    }
    public static void main(String[] args) {
        Yard yy = new Yard();
        yy.launch();
    }

    public void stop() {
        gameOver = true;
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            s.keyPressed(e);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
