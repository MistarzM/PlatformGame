package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

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
        levelOneBackgroundGIF = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF, 2.0/3.0);
    }

    private void importLevelBuildSprites() {            // !!!to replace -> important -> create design for hit boxes -> level_one_hitBoxes.png

        levelBuildImg = new BufferedImage[2]; // tab 61 x 19

        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.MAIN_LEVEL);

        levelBuildImg[0] = img.getSubimage(192, 272, 16, 16);     //color
        levelBuildImg[1] = img.getSubimage(0, 0, 16, 16);       //white
    }

    public void draw(Graphics g, int xLevelOffset){
        //BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.LEVEL_ONE_DESIGN);
        levelOneBackgroundGIF.paintIcon(null, g, 0, 0);
        //g.drawImage(img, -xLevelOffset, 0, (int)(img.getWidth()* Game.SCALE), (int)(img.getHeight() * SCALE), null);
    }

    public void update(){

    }

    public Level getLevelOne(){
        return levelOne;
    }
}
