package main;

import java.lang.Runnable;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;    // Frames per second for our game

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();       // used to focus on a certain component
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long previousCheck = System.currentTimeMillis();

        while(true){        //infinite loop

            now = System.nanoTime();
            if(now - lastFrame >= timePerFrame){

                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if(System.currentTimeMillis() - previousCheck >= 1000 ){
                previousCheck = System.currentTimeMillis();
                System.out.println(frames + " FPS");
                frames = 0;
            }
        }

    }
}
