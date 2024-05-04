package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import main.Game;
import utils.LoadAndSave;

public class LevelHandler {

    private Game game;
    private BufferedImage levelBuildImg;

    public LevelHandler(Game game){
        this.game = game;
        this.levelBuildImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.MAIN_LEVEL);

    }

    public void draw(Graphics g){
        g.drawImage(levelBuildImg, 0, 0, null);
    }

    public void update(){

    }
}
