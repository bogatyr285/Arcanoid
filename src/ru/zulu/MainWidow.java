package ru.zulu;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import ru.zulu.core.GameManager;
import ru.zulu.core.Models.Ball;
import ru.zulu.core.Models.Stick;
import ru.zulu.core.views.BaseView;

public class MainWidow {

    private JFrame frame;
    private BaseView view;
    private GameManager gameManager;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWidow window = new MainWidow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the application.
     */
    public MainWidow() {
        frame = new JFrame();
        frame.setBounds(0,0,640,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Arcanoid");
        view = new BaseView();
        frame.getContentPane().add(view);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        final JMenuItem miStartGame = new JMenuItem("Start game");
        menuBar.add(miStartGame);


        final JMenuItem miStopGame = new JMenuItem("Stop game");
        miStopGame.setEnabled(false);
        menuBar.add(miStopGame);

        miStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.start();
                miStartGame.setEnabled(false);
                miStopGame.setEnabled(true);
            }
        });
        miStopGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.stop();
                miStartGame.setEnabled(true);
                miStopGame.setEnabled(false);
            }
        });


       gameManager = new GameManager(view);
      frame.addKeyListener(gameManager);

    }
}
