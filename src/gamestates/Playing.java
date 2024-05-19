package gamestates;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import userinterface.PauseMenu;
import utils.LoadAndSave;
import userinterface.GameOver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Playing extends State implements StateMethods{

    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private PauseMenu pauseMenu;
    private GameOver gameOver;
    private boolean paused= false;

    private int xLevelOffset;
    private int leftBorder = (int) (0.25 * Game.PANEL_WIDTH);
    private int rightBorder = (int) (0.75 * Game.PANEL_WIDTH);
    private int maxLevelOffsetX;

    private boolean isGameOver;
    private boolean changeLevel;

    public Playing(Game game) {
        super(game);
        initClasses();

        calculateLevelOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        resetAll();
        levelHandler.loadNextLevel();
        player.setPlayerSpawn(levelHandler.getCurrentLevel().getLevelSpawn());
    }

    private void loadStartLevel() {
        enemyHandler.loadEnemies(levelHandler.getCurrentLevel());
    }

    private void calculateLevelOffset(){
        maxLevelOffsetX = levelHandler.getCurrentLevel().getMaxLevelOffsetX();
    }

    private void initClasses() {
        player = new Player(960, 16, (int) (256 * Game.SCALE), (int) (128 * Game.SCALE), this);
        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler(this);
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        player.setPlayerSpawn(levelHandler.getCurrentLevel().getLevelSpawn());
        pauseMenu = new PauseMenu(this);
        gameOver = new GameOver(this);
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
        if(paused || isGameOver) {
            pauseMenu.update();
        } else if(changeLevel){
            loadNextLevel();
            changeLevel = false;
        }else {
            levelHandler.update();
            player.update();
            enemyHandler.update(levelHandler.getCurrentLevel().getLevelData(), player);
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

    public void resetAll(){
        isGameOver = false;
        paused = false;
        changeLevel = false;
        player.reset();
        enemyHandler.resetEnemies();
    }

    public LevelHandler getLevelHandler(){
        return levelHandler;
    }

    public void checkEnemyHitBox(Rectangle2D.Float attackHitBox){
        enemyHandler.checkEnemyHurt(attackHitBox);
    }

    public void setGameOver(boolean isGameOver){
        this.isGameOver = isGameOver;
    }

    public void setChangeLevel(boolean changeLevel){
        this.changeLevel = changeLevel;
    }

    public EnemyHandler getEnemyHandler(){
        return enemyHandler;
    }

    public void setMaxLevelOffsetX(int maxLevelOffsetX){
        this.maxLevelOffsetX = maxLevelOffsetX;
    }

    @Override
    public void draw(Graphics g) {
        levelHandler.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyHandler.draw(g, xLevelOffset);

        if(paused) {
            g.setColor(new Color(0, 0, 0, 128));
            g.fillRect(0, 0, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);
            pauseMenu.draw(g);
        } else if(isGameOver){
            gameOver.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if(!isGameOver) {
            if (paused) {
                pauseMenu.mouseDragged(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!isGameOver) {
            if (paused) {
                pauseMenu.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!isGameOver) {
            if (paused) {
                pauseMenu.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoves(MouseEvent e) {
        if(!isGameOver) {
            if (paused) {
                pauseMenu.mouseMoves(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(isGameOver){
            gameOver.pressEnterToContinue(e);
        } else {
            switch (e.getKeyCode()) {
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
                case KeyEvent.VK_E:
                    player.setInteraction(true);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!isGameOver) {
            switch (e.getKeyCode()) {
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
                case KeyEvent.VK_E:
                    player.setInteraction(false);
                    break;
            }
        }
    }
}
