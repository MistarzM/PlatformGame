package levels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.GameState;
import main.Game;
import utils.LoadAndSave;

import javax.swing.*;

import static main.Game.*;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelBuildImg;
    private ArrayList<Level> levels;
    private ImageIcon levelOneBackgroundGIF;

    private int levelIndex = 0;

    public LevelHandler(Game game){
        this.game = game;
        levels = new ArrayList<>();
        buildLevels();
        levelOneBackgroundGIF = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_GIF_SCALE);
    }

    private void buildLevels() {
        BufferedImage[] buildLevels = LoadAndSave.GetAllLevels();
        for(BufferedImage img : buildLevels){
            levels.add(new Level(img));
        }
    }

    public void loadNextLevel(){
        levelIndex++;
        if(levelIndex>= levels.size()){
            System.out.println("Win");
            levelIndex = 0;
            GameState.gameState = GameState.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyHandler().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getMaxLevelOffsetX());
    }

    public void draw(Graphics g, int xLevelOffset){
        BufferedImage[] img = LoadAndSave.GetSpriteAtlas(LoadAndSave.LEVEL_DESIGN);
        g.setColor(new Color(25, 33, 40, 255));
        g.fillRect(0, 0, 1920, 1080);
        levelOneBackgroundGIF.paintIcon(null, g, 0, 0);
        g.drawImage(img[levelIndex], -xLevelOffset, 0, (int)(img[levelIndex].getWidth()* Game.SCALE), (int)(img[levelIndex].getHeight() * SCALE), null);
    }

    public void update(){

    }

    public Level getCurrentLevel(){
        return levels.get(levelIndex);
    }

    public int getHowManyLevels(){
        return levels.size();
    }
}
