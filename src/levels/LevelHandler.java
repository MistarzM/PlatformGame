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

    private void importLevelBuildSprites() {

        levelBuildImg = new BufferedImage[1159]; // tab 61 x 19

        BufferedImage img = LoadAndSave.GetSpriteAtlas(LoadAndSave.MAIN_LEVEL);

        for(int j = 0; j < 19; j++){
            for(int i = 0; i < 61; i ++){
                int temp = j * 61 + i;
                levelBuildImg[temp] = img.getSubimage((i*16) + 32, (j*16) + 48, 16, 16);
            }
        }
    }

    public void draw(Graphics g){

        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++){
            for(int i = 0; i < Game.TILES_IN_WIDTH; i++){
                int temp = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelBuildImg[temp], i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    public void update(){

    }

    public Level getLevelOne(){
        return levelOne;
    }
}
