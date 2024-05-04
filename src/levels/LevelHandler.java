package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import main.Game;
import utils.LoadAndSave;

import static main.Game.*;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelBuildImg;
    private Level levelOne;

    public LevelHandler(Game game){
        this.game = game;
        importLevelBuildSprites();
        levelOne = new Level(LoadAndSave.GetLevelData());
    }

    private void importLevelBuildSprites() {            // !!!to replace -> important -> create design for hit boxes -> level_one_hitBoxes.png

        levelBuildImg = new BufferedImage[2]; // tab 61 x 19

        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.MAIN_LEVEL);

        levelBuildImg[0] = img.getSubimage(192, 272, 16, 16);     //color
        levelBuildImg[1] = img.getSubimage(0, 0, 16, 16);       //white
    }

    public void draw(Graphics g){
        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.LEVEL_ONE_DESIGN);

        g.drawImage(img, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
    }

    public void update(){

    }

    public Level getLevelOne(){
        return levelOne;
    }
}
