package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameOver {

    private Playing playing;
    private BufferedImage[] keyboardInteraction;
    private BufferedImage defeat;

    public GameOver(Playing playing){
        this.playing = playing;
        loadAnimations();
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0, 140));
        g.fillRect(0,0, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);

        g.drawImage(defeat, Game.PANEL_WIDTH/2 - defeat.getWidth(), Game.PANEL_HEIGHT/2 - defeat.getHeight(), (int)(189 * 2 * Game.SCALE), (int)(30 * 2 * Game.SCALE), null);
        g.drawImage(keyboardInteraction[7], Game.PANEL_WIDTH - 8 * Game.TILE_SIZE, Game.PANEL_HEIGHT - 4 * Game.TILE_SIZE, (int)(32 * 3 * Game.SCALE), (int)(16 * 3 * Game.SCALE), null);
    }

    public void pressEnterToContinue(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            GameState.gameState = GameState.MENU;
        }
    }

    public void loadAnimations(){
        BufferedImage keyExtrasImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.KEYBOARD_EXTRAS);

        defeat = LoadAndSave.GetSpriteAtlas(LoadAndSave.DEFEAT);

        keyboardInteraction = new BufferedImage[16];

        for(int i = 0; i < keyboardInteraction.length; i++){
            keyboardInteraction[i] = keyExtrasImg.getSubimage((i%4) * 32, (i/4) * 16, 32, 16);
        }
    }
}
