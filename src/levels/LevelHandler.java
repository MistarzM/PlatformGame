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
        importLevelBuildSprites();
        levelOne = new Level(LoadAndSave.GetLevelData());
        levelOneBackgroundGIF = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_GIF_SCALE);
    }

    private void importLevelBuildSprites() {            // !!!to replace -> important -> create design for hit boxes -> level_one_hitBoxes.png

        levelBuildImg = new BufferedImage[2]; // tab 61 x 19

        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.MAIN_LEVEL);

        levelBuildImg[0] = img.getSubimage(192, 272, 16, 16);     //color
        levelBuildImg[1] = img.getSubimage(0, 0, 16, 16);       //white
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
