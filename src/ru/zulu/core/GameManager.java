package ru.zulu.core;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import ru.zulu.core.Models.Ball;
import ru.zulu.core.Models.Block;
import ru.zulu.core.Models.Bonus.*;
import ru.zulu.core.Models.Stick;
import ru.zulu.core.views.BaseView;
import utils.ResourcesLoader;

public class GameManager implements BaseView.EventsListener, KeyListener {
    private enum States {
        IDLE,
        RUNNING,
        PLAYING,
        LOSE,
        WIN
    }

    // =============================================================================================
    // FIELDS
    // =============================================================================================
    private final BaseView view;
    private int width;
    private int height;
    private States state;
    private int ups;
    private int fps;
    Random random = new Random();


    private Ball ball;
    private int life;
    private static int countAllBlock;//for determine win
    private static int countDownedBlock;
    private ArrayList<Block> blocks = new ArrayList<>();
    private Stick stick;
    private Bonus bonus;

    static protected Image spriteLose;
    static protected Image spriteWin;
    static protected Image spriteLifeBonus;


    // =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public GameManager(BaseView _view) {
        view = _view;
        view.setEventsListener(this);//call GameManager

        state = States.IDLE;
        spriteLose = ResourcesLoader.loadDrawableIgnoreErrors("lose2.png");
        spriteWin = ResourcesLoader.loadDrawableIgnoreErrors("win2.png");
        spriteLifeBonus = ResourcesLoader.loadDrawableIgnoreErrors("Heart_bonus.png");

    }


    // =============================================================================================
    // METHODS
    // =============================================================================================
    private final Runnable threadTask = new Runnable() {
        @Override
        public void run() {

            state = States.RUNNING;

            long lastTime = System.nanoTime();
            double delta = 0.0;
            double ns = 1000000000.0 / 60.0;
            long timer = System.currentTimeMillis();
            int updates = 0;
            int frames = 0;
            while (state == States.RUNNING || state == States.PLAYING) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (delta >= 1.0) {
                    update();
                    updates++;
                    delta--;
                }
                render();
                frames++;
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    ups = updates;
                    fps = frames;
                    updates = 0;
                    frames = 0;
                }
            }
        }
    };

    public void start() {
        if (state != States.RUNNING) {

            init();
            new Thread(threadTask).start();
        }
    }

    private void init() {
        life = 2;
        countAllBlock = 0;
        countDownedBlock = 0;
        //create blocks
        int x = 10;
        int y = 10;
        int gap = 5;
        blocks.clear();


        for (int i = 0; i < (width / (Block.WIDTH + gap) * 3); i++) {

            Block block = new Block(x, y);
            countAllBlock++;
            blocks.add(block);
            x += (block.width + gap);
            if (x > width) {
                x = 10;
                y += block.height + gap;
            }
        }


        //create stick
        stick = new Stick(width / 2 - Stick.WIDTH / 2, height - Stick.HEIGHT - 20);
        //create ball
        ball = new Ball(stick.x + stick.width / 2 - Ball.DIAMETR / 2, stick.y - Ball.DIAMETR);


        bonus = new Bonus("Heart_bonus");
        bonus.yVelocity = 2;

    }

    public void stop() {
        state = States.IDLE;
        view.repaint();
    }

    // =============================================================================================
    // UPDATING
    // =============================================================================================
    private void update() {


        //  move
        ball.move();
        // 2. walls collision
        if (ball.x <= 0) {//left
            ball.xVelocity *= -1;
            ball.x = 0;
        }

        if (ball.x + ball.width >= width) {//right
            ball.xVelocity *= -1;
            ball.x = width - ball.width;
        }

        if (ball.y <= 0) {//top
            ball.yVelocity *= -1;
            ball.y = 0;
        }

        stick.move();
        if (stick.x <= 0) {//bound for stick
            stick.x = 0;
        } else if (stick.x + stick.width >= width) {
            stick.x = width - stick.width;
        }
        if (state == States.RUNNING) {
            ball.x = stick.x + stick.width / 2 - Ball.DIAMETR / 2;
            ball.y = stick.y - Ball.DIAMETR;
        }
        for (int j = 0; j < blocks.size(); j++) {
            Block block = blocks.get(j);
            if (block.visible && ball.isIntersects(block)) {//block collision

                block.visible = false;
                ball.yVelocity *= -1;
                countDownedBlock++;


                if (random.nextFloat() < 0.8 && !bonus.isVisible)//create bonus
                {
                    bonus.isVisible = true;

                    //bonus.type = Bonus.Type.values()[random.nextInt(Bonus.Type.values().length)];
                    Random rand = new Random();
                    switch (rand.nextInt(3)+1) {
                        case 1:
                        Life life=new Life();

                        break;
                        case 2:
                            break;
                    }
                    bonus.x = block.x + block.width / 2;//down from block center
                    bonus.y = block.y + block.height;
                }
            }
            if (ball.isIntersects(stick)) { //rebound from stick
                ball.yVelocity *= -1;
            }

        }
        if (ball.y > stick.y)//if ball out
        {
            life--;
            state = States.RUNNING;
            stick.x = width / 2 - Stick.WIDTH / 2;//reset stick coord
            stick.y = height - Stick.HEIGHT - 20;

            ball.x = stick.x + stick.width / 2 - Ball.DIAMETR / 2;//reset ball coord
            ball.y = stick.y - Ball.DIAMETR;
            ball.xVelocity = 0;
            ball.yVelocity = 0;
            if (life == 0) {
                state = States.LOSE;
            }
        }

        if (bonus.isVisible) {
            bonus.move();
            if (stick.isIntersects(bonus)) {
                long timer = System.currentTimeMillis();
                //System.out.println("Add extra life");
                //switch (bonus.type) {
                 //   case LIFE:
                  //      life++;
                   //     break;
                   // case SPEED:
                   //    Stick.velosityStick += 10;
                   //     break;                }
                bonus.isVisible = false;

            }
            if (bonus.y > stick.y + stick.height) {
                bonus.isVisible = false;

            }
        }
        //  if(System.currentTimeMillis()-Bonus.DURATION>timer)
        // {   }


        if (countAllBlock == countDownedBlock) {
            state = States.WIN;
        }

    }

    private void render() {
        view.repaint();
    }

    @Override
    public void onDraw(Graphics _graphics) {
        _graphics.drawString("ups=" + ups + ", fps=" + fps + ", life= " + life, 10, 10);

        switch (state) {
            case IDLE://DO  nothing
                break;
            case LOSE:
                _graphics.drawImage(spriteLose, width / 2 - 144, 50, null);
                break;
            case WIN:
                _graphics.drawImage(spriteWin, width / 2 - 144, 50, null);
                break;
            default:
                for (Block block : blocks) {
                    block.draw(_graphics);
                }
                stick.draw(_graphics);
                ball.draw(_graphics);
                bonus.draw(_graphics);
        }
    }


    @Override
    public void onResize(int _width, int _height) {
        width = _width;
        height = _height;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                if (state == States.RUNNING) {
                    state = States.PLAYING;
                    ball.xVelocity = -7;
                    ball.yVelocity = -7;
                }
                break;
            case KeyEvent.VK_LEFT:
                stick.xVelocity = -(Stick.velosityStick);
                break;
            case KeyEvent.VK_RIGHT:
                stick.xVelocity = (Stick.velosityStick);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                stick.xVelocity = 0;
                break;
        }

    }
}
