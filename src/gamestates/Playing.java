package gamestates;

import entities.Player;
import levels.LevelHandler;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods{

    private Player player;
    private LevelHandler levelHandler;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        player = new Player(640, 176, (int) (256 * Game.SCALE), (int) (128 * Game.SCALE));
        levelHandler = new LevelHandler(game);
        player.loadLevelData(levelHandler.getLevelOne().getLevelData());
    }

    public void windowFocusLost(){
        player.resetDirectionBoolean();
    }

    public Player getPlayer(){
        return player;
    }

    @Override
    public void update() {
        levelHandler.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelHandler.draw(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoves(MouseEvent e) {

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
                GameState.gameState = GameState.MENU;
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
