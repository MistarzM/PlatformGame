package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class GameOver {

    private Playing playing;

    public GameOver(Playing playing){
        this.playing = playing;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0, 180));
        g.fillRect(0,0, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", Game.PANEL_WIDTH / 2, 150);
        g.drawString("Press Enter to return to the Main Menu!", Game.PANEL_WIDTH/2 , 300);
    }

    public void pressEnterToContinue(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            GameState.gameState = GameState.MENU;
        }
    }
}
