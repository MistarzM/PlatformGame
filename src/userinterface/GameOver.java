package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameOver extends GameResult{

    public GameOver(Playing playing){
        super(playing);
    }

    public void draw(Graphics g){
        draw(g, false);
    }


    public void pressEnterToContinue(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            GameState.gameState = GameState.MENU;
        }
    }

}
