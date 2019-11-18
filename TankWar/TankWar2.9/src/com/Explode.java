package com;

import java.awt.*;

/**
 * @author Chan
 * @version 1.0
 * @date 2019/10/22 15:10
 */
public class Explode {
    int x, y;
    private boolean live = true;

    private TankClient tc;

    //    int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] imgs = {
        tk.getImage(Explode.class.getClassLoader().getResource("images//0.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//1.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//2.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//3.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//4.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//5.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//6.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//7.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//8.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//9.gif")),
        tk.getImage(Explode.class.getClassLoader().getResource("images//10.gif"))
    };

    int step = 0;

    private static boolean init = false;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if(!init) {
            for (int i = 0; i < imgs.length; i++) {
                g.drawImage(imgs[i], -100, -100, null);
            }
            init = true;
        }

        if(!live) {
            tc.explodes.remove(this);
            return;
        }

        if(step == imgs.length) {
//            step = 0;
            this.live = false;
            return;
        }

//        Color c = g.getColor();
//        g.setColor(Color.ORANGE);
        g.drawImage(imgs[step], x, y, null);
//        g.setColor(c);

        step ++;
    }
}
