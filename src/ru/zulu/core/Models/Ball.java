package ru.zulu.core.Models;

import java.awt.Graphics;
import java.awt.*;
import java.util.Random;

public class Ball extends DrawableObject {

    public Color color;
    public final static int DIAMETR=10;

    public Ball(int _x, int _y) {
        x = _x;
        y = _y;
        width = DIAMETR;
        height = DIAMETR;
        color = Color.green;
    }

    public void draw(Graphics _g) {
        _g.setColor(color);
        _g.fillOval(x , y, width,height);
    }
}
