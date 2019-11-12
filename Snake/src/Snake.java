import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/16 21:50
 */
public class Snake {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private Node n = new Node(10,20,Dir.L);
    private Yard y = null;

    public Snake(Yard y) {
        this.head = n;
        this.tail = n;
        size = 1;
        this.y = y;
    }

    public void draw(Graphics g) {
        if(size <= 0) return;
        move();
        for(Node node = head; node != null; node = node.next) {
            node.draw(g);
        }
    }

    private void move() {
        addToHead();
        deleteFromTail();
        checkDead();
    }

    private void checkDead() {
        if(head.row < 2|| head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS) {
            this.y.stop();
        }

        for(Node n = head.next; n != null; n = n.next) {
            if(head.row == n.row && head.col == n.col) {
                this.y.stop();
            }
        }
    }

    private void deleteFromTail() {
        if(tail == null) return;
        tail = tail.prev;
        tail.next = null;
    }

    public void addToTail() {
        Node node = null;
        switch (tail.dir) {
            case L:
                node = new Node(tail.row, tail.col + 1, tail.dir);
                break;
            case U:
                node = new Node(tail.row + 1, tail.col, tail.dir);
                break;
            case R:
                node = new Node(tail.row, tail.col - 1, tail.dir);
                break;
            case D:
                node = new Node(tail.row - 1, tail.col, tail.dir);
                break;
        }
        this.tail.next = node;
        node.prev = this.tail;
        this.tail = node;
        size ++;
    }

    public void addToHead() {
        Node node = null;
        switch (head.dir) {
            case L:
                node = new Node(head.row, head.col - 1, head.dir);
                break;
            case U:
                node = new Node(head.row - 1, head.col, head.dir);
                break;
            case R:
                node = new Node(head.row, head.col + 1, head.dir);
                break;
            case D:
                node = new Node(head.row + 1, head.col, head.dir);
                break;
        }
        node.next = this.head;
        this.head.prev = node;
        this.head = node;
        size ++;
    }


    private class Node {
        int w = Yard.BLOCK_SIZE;
        int h = Yard.BLOCK_SIZE;
        int row, col;
        Dir dir;
        Node next = null;
        Node prev = null;

        Node(int row, int col, Dir dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(w * col, h * row, w, h);
            g.setColor(c);
        }
    }

    public void eat(Egg e) {
        if(this.getRectangle().intersects(e.getRectangle())) {
            e.reAppear();
            this.addToHead();
            this.y.setScore(this.y.getScore() + 5);
        }
    }

    private Rectangle getRectangle() {
        return new Rectangle(head.col * Yard.BLOCK_SIZE, head.row * Yard.BLOCK_SIZE, head.w, head.h);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                if(head.dir != Dir.R) head.dir = Dir.L;
                break;
            case KeyEvent.VK_UP:
                if(head.dir != Dir.D) head.dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                if(head.dir != Dir.L) head.dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:
                if(head.dir != Dir.U) head.dir = Dir.D;
                break;
            case KeyEvent.VK_ENTER:
                this.init();

        }
//        System.out.println("key pressed!" + head.dir);
    }

    public void init() {
        this.head = n;
        this.tail = n;
        size = 1;
        this.y.init();
        this.notify();
    }
}
