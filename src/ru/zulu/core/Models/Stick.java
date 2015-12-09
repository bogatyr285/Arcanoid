package ru.zulu.core.Models;

import utils.ResourcesLoader;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Stick extends DrawableObject{

    public final static int WIDTH=128;
    public final static int HEIGHT=16;
    public  static int velosityStick=7;


    protected Image sprite;

   public Stick(int _x,int _y){
        x=_x;
        y=_y;

        width=WIDTH;
        height=HEIGHT;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Stick.png");
    }

    public  void draw(Graphics _g) {
        _g.drawImage(sprite,x,y, null);
    }
}
