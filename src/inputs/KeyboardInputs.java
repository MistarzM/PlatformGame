package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {           // events for keyboard   (extends -> class, implements -> interface)

    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'd'){
            gamePanel.setMoveRight(((int)gamePanel.getMoveRight())+10);
        }
        if (e.getKeyChar() == 'a'){
            gamePanel.setMoveRight(((int)gamePanel.getMoveRight())-10);
        }
        if(e.getKeyChar() == 'w'){
            gamePanel.setMoveDown(((int)gamePanel.getMoveDown())-10);
        }
        if(e.getKeyChar() == 's'){
            gamePanel.setMoveDown(((int)gamePanel.getMoveDown())+10);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
