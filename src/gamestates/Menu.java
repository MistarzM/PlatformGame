package gamestates;

import main.Game;
import userinterface.ButtonInMenu;
import utils.LoadAndSave;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon; // for GIF in MENU

public class Menu extends State implements StateMethods {

    private ButtonInMenu[] buttons = new ButtonInMenu[3];
    private BufferedImage backgroundMenuImage;
    private int menuXPosition, menuYPosition, menuWidth, menuHeight;
    private ImageIcon backgroundMenuGIF;


    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        backgroundMenuGIF = LoadAndSave.GetGIF(LoadAndSave.MENU_BACKGROUND, LoadAndSave.MENU_GIF_SCALE);

    }

    private void loadButtons() {
        buttons[0] = new ButtonInMenu(Game.PANEL_WIDTH /5, (int)(170 * Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new ButtonInMenu(Game.PANEL_WIDTH /5, (int)(270 * Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new ButtonInMenu(Game.PANEL_WIDTH /5, (int)(370 * Game.SCALE), 2, GameState.QUIT);
    }

    private void loadBackground() {
        BufferedImage background = LoadAndSave.GetSpriteAtlas(LoadAndSave.GUI_BACKGROUND);
        backgroundMenuImage = background.getSubimage(78, 87, 64, 64);

        menuWidth = (int) (background.getWidth()* 3 * Game.SCALE);
        menuHeight = (int) (background.getHeight()* 3 * Game.SCALE);

        menuXPosition = Game.PANEL_WIDTH / 5 - menuWidth/2;
        menuYPosition = (int) ( 60 * Game.SCALE);
    }

    @Override
    public void update() {
        for(ButtonInMenu b : buttons){
            b.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        backgroundMenuGIF.paintIcon(null, g, 0, 0);

        g.drawImage(backgroundMenuImage, menuXPosition, menuYPosition, menuWidth, menuHeight, null);

        for(ButtonInMenu b : buttons){
            b.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(ButtonInMenu b : buttons){
            if(cursorOnButton(e, b)){
               b.setMousePressed(true);
               break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(ButtonInMenu b : buttons){
            if(b.getMousePressed()){
                b.setGameState();
                break;
            }
        }
        resetMenuButtons();
    }

    private void resetMenuButtons() {
        for(ButtonInMenu b : buttons){
            b.resetMenuButtons();
        }
    }

    @Override
    public void mouseMoves(MouseEvent e) {
        for(ButtonInMenu b : buttons){
            b.setMouseHover(false);
        }

        for(ButtonInMenu b : buttons){
            if(cursorOnButton(e, b)){
                b.setMouseHover(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            GameState.gameState = GameState.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
