package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import main.Game;
import utils.LoadAndSave;

import static main.Game.TILE_SIZE;

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

        levelBuildImg[0] = img.getSubimage(32, 48, 16, 16);
        levelBuildImg[1] = img.getSubimage(0, 0, 16, 16);
    }

    public void draw(Graphics g){

        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++){
            for(int i = 0; i < Game.TILES_IN_WIDTH; i++){
                int temp = levelOne.getSpriteIndex(i, j);
                g.drawImage(temp == 1 ? levelBuildImg[1] : levelBuildImg[0], i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    public void update(){

    }

    public Level getLevelOne(){
        return levelOne;
    }
}
