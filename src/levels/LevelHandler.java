package levels;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadAndSave;

import javax.swing.*;

import static main.Game.*;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelBuildImg;
    private Level levelOne;
    private ImageIcon levelOneBackgroundGIF;

    public LevelHandler(Game game){
        this.game = game;
        levelOne = new Level(LoadAndSave.GetLevelData());
        levelOneBackgroundGIF = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_GIF_SCALE);
    }


    public void draw(Graphics g, int xLevelOffset){
        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.LEVEL_ONE_DESIGN);
        g.setColor(new Color(25, 33, 40, 255));
        g.fillRect(0, 0, 1920, 1080);
        levelOneBackgroundGIF.paintIcon(null, g, 0, 0);
        g.drawImage(img, -xLevelOffset, 0, (int)(img.getWidth()* Game.SCALE), (int)(img.getHeight() * SCALE), null);
    }

    public void update(){

    }

    public Level getLevelOne(){
        return levelOne;
    }
}
