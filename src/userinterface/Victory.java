package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import main.GamePanel;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Victory {

    private Playing playing;
    private BufferedImage[] keyboardInteraction;
    private BufferedImage victory;

    public Victory(Playing playing){
        this.playing = playing;
        loadAnimations();
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0, 140));
        g.fillRect(0,0, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);

        g.drawImage(victory, Game.PANEL_WIDTH/2 - victory.getWidth(), Game.PANEL_HEIGHT/2 - victory.getHeight(), (int)(162 * 2 * Game.SCALE), (int)(30 * 2 * Game.SCALE), null);
        g.drawImage(keyboardInteraction[7], Game.PANEL_WIDTH - 8 * Game.TILE_SIZE, Game.PANEL_HEIGHT - 4* Game.TILE_SIZE, (int)(32 * 3 * Game.SCALE), (int)(16 * 3 * Game.SCALE), null);
    }

    public void pressEnterToContinue(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.resetAll();
            playing.setChangeLevel(true);
        }
    }

    public void loadAnimations(){
        BufferedImage keyExtrasImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.KEYBOARD_EXTRAS);

        victory = LoadAndSave.GetSpriteAtlas(LoadAndSave.VICTORY);

        keyboardInteraction = new BufferedImage[16];

        for(int i = 0; i < keyboardInteraction.length; i++){
            keyboardInteraction[i] = keyExtrasImg.getSubimage((i%4) * 32, (i/4) * 16, 32, 16);
        }
    }
}
