package ru.zulu.core.Models.Bonus;

import ru.zulu.core.Models.DrawableObject;
import utils.ResourcesLoader;

import java.awt.*;

/**
 * Created by user142 on 05.12.2015.
 */
public class Bonus extends DrawableObject {

    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int DURATION = 10000;

    public boolean isVisible;
    private final Image SPRITE;
    public Bonus(String _img) {

        width = WIDTH;
        height = HEIGHT;
        SPRITE = ResourcesLoader.loadDrawableIgnoreErrors(_img+".png");

    }

    public void draw(Graphics _g) {
        if (isVisible) {

                _g.drawImage(SPRITE, x, y, WIDTH, HEIGHT, null);

            }

        }
    }


