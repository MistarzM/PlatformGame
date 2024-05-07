package gamestates;

import main.Game;
import userinterface.ButtonInMenu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Menu extends State implements StateMethods {

    private ButtonInMenu[] buttons = new ButtonInMenu[3];

    public Menu(Game game) {
        super(game);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new ButtonInMenu(Game.PANEL_WIDTH /2, (int)(250 * Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new ButtonInMenu(Game.PANEL_WIDTH / 2, (int)(350 * Game.SCALE), 1, GameState.OPTIONS);
        buttons[2] = new ButtonInMenu(Game.PANEL_WIDTH/2, (int)(450 * Game.SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for(ButtonInMenu b : buttons){
            b.update();
        }
    }

    @Override
    public void draw(Graphics g) {
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
