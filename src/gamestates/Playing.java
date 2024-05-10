package gamestates;

import entities.Player;
import levels.LevelHandler;
import main.Game;
import userinterface.PauseMenu;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods{

    private Player player;
    private LevelHandler levelHandler;
    private PauseMenu pauseMenu;
    private boolean paused= false;

    private int xLevelOffset;
    private int leftBorder = (int) (0.25 * Game.PANEL_WIDTH);
    private int rightBorder = (int) (0.75 * Game.PANEL_WIDTH);
    private int levelTilesWide = LoadAndSave.GetLevelData()[0].length;
    private int maxTileOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTileOffset * Game.TILE_SIZE;


    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        player = new Player(960, 16, (int) (256 * Game.SCALE), (int) (128 * Game.SCALE));
        levelHandler = new LevelHandler(game);
        player.loadLevelData(levelHandler.getLevelOne().getLevelData());
        pauseMenu = new PauseMenu(this);
    }

    public void windowFocusLost(){
        player.resetDirectionBoolean();
    }

    public Player getPlayer(){
        return player;
    }

    public void unpauseGame() {
        paused = false;
    }

    @Override
    public void update() {
        if(paused) {
            pauseMenu.update();
        } else {
            levelHandler.update();
            player.update();
            checkIfPlayerCloseToBorder();
        }
    }

    private void checkIfPlayerCloseToBorder() {
        int playerX = (int) (player.getHitBox().x);
        int difference = playerX - xLevelOffset;

        if(difference> rightBorder){
            xLevelOffset += difference - rightBorder;
        } else if (difference < leftBorder){
            xLevelOffset += difference - leftBorder;
        }

        if(xLevelOffset > maxLevelOffsetX){
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0){
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelHandler.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);

        if(paused) {
            pauseMenu.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if(paused) {
            pauseMenu.mouseDragged(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused) {
            pauseMenu.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused) {
            pauseMenu.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoves(MouseEvent e) {
        if(paused) {
            pauseMenu.mouseMoves(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_J:
                player.setAttacking(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }
}
