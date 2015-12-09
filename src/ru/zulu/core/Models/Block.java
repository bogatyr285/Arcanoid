package ru.zulu.core.Models;

import utils.ResourcesLoader;

import java.awt.*;


public class Block extends DrawableObject {

    public boolean visible;
    static protected Image sprite;
    public final static int WIDTH =64;

    public Block(int _x, int _y) {
        x = _x;
        y = _y;
        width = 64;
        height = 22;

        visible = true;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Wall.png");
    }

    public void draw(Graphics _g) {
        if (visible) {
            _g.drawImage(sprite, x, y, null);
        }
    }
}
