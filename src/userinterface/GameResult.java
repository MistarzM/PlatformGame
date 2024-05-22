package userinterface;

import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameResult {

    protected Playing playing;
    private BufferedImage victory, defeat;
    private BufferedImage[] keyboardExtras;

     public GameResult(Playing playing){
         this.playing = playing;
         loadImg();
     }

    protected void draw(Graphics g, boolean isVictory){
        g.setColor(new Color(0,0,0, 140));
        g.fillRect(0,0, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);
        BufferedImage img = isVictory ? victory : defeat;
        g.drawImage(img, Game.PANEL_WIDTH/2 - img.getWidth(), Game.PANEL_HEIGHT/2 - img.getHeight(), (int)(img.getWidth() * 2 * Game.SCALE), (int)(img.getHeight() * 2 * Game.SCALE), null);
        g.drawImage(keyboardExtras[7], Game.PANEL_WIDTH - 8 * Game.TILE_SIZE, Game.PANEL_HEIGHT - 4 * Game.TILE_SIZE, (int)(32 * 3 * Game.SCALE), (int)(16 * 3 * Game.SCALE), null);
    }

    public void loadImg(){
        BufferedImage keyExtrasImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.KEYBOARD_EXTRAS);

        defeat = LoadAndSave.GetSpriteAtlas(LoadAndSave.DEFEAT);
        victory = LoadAndSave.GetSpriteAtlas(LoadAndSave.VICTORY);

        keyboardExtras = new BufferedImage[16];

        for(int i = 0; i < keyboardExtras.length; i++){
            keyboardExtras[i] = keyExtrasImg.getSubimage((i%4) * 32, (i/4) * 16, 32, 16);
        }
    }
}
