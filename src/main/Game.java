package main;

import java.awt.*;
import java.lang.Runnable;

import entities.Player;
import levels.LevelHandler;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;    // Frames per second -> draws the game(level, enemies)
    private final int UPS_SET = 200;    // Updates per second -> takes care of logic (events)

    public final static int TILE_INIT_SIZE = 16;
    public final static float SCALE = 1.3f;
    public final static int TILES_IN_WIDTH = 80;
    public final static int TILES_IN_HEIGHT = 45;
    public final static int TILE_SIZE = (int)(TILE_INIT_SIZE * SCALE);
    public final static int PANEL_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
    public final static int PANEL_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;

    private Player player;
    private LevelHandler levelHandler;

    public Game(){
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();       // used to focus on a certain component

        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200, 200, (int) (256 * SCALE), (int) (128 * SCALE));
        levelHandler = new LevelHandler(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        player.update();
        levelHandler.update();
    }

    public void render(Graphics g){
        levelHandler.draw(g);
        player.render(g);
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long previousCheck = System.currentTimeMillis();

        double deltaUpdates = 0;
        double deltaFrames = 0;

        while(true){        //infinite loop
            long currentTime = System.nanoTime();

            deltaUpdates += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaUpdates >= 1){
                update();
                deltaUpdates--;
                updates++;
            }

            if(deltaFrames >= 1){
                gamePanel.repaint();
                deltaFrames--;
                frames++;
            }

            if(System.currentTimeMillis() - previousCheck >= 1000 ){
                previousCheck = System.currentTimeMillis();
                System.out.println(frames + " FPS | " + updates + " UPS");
                frames = 0;
                updates = 0;
            }
        }

    }

    public void windowFocusLost(){
        player.resetDirectionBoolean();
    }

    public Player getPlayer(){
        return player;
    }
}
