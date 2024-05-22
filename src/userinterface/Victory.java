package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import main.GamePanel;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Victory extends GameResult{

    public Victory(Playing playing){
        super(playing);
    }

    public void draw(Graphics g){
        draw(g, true);
    }

    public void pressEnterToContinue(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            playing.setChangeLevel(true);
        }
    }
}
