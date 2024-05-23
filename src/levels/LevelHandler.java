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
    private BufferedImage[] imgDesign;
    private ImageIcon[] levelBackgroundGIF;

    private int levelIndex = 0;

    public LevelHandler(Game game){
        this.game = game;
        levels = new ArrayList<>();
        buildLevels();
        loadGIF();
        imgDesign = LoadAndSave.GetSpriteAtlas(LoadAndSave.LEVEL_DESIGN);
    }

    private void buildLevels() {
        BufferedImage[] buildLevels = LoadAndSave.GetAllLevels();
        for(BufferedImage img : buildLevels){
            levels.add(new Level(img));
        }
    }

    private void loadGIF(){
        levelBackgroundGIF = new ImageIcon[3];
        levelBackgroundGIF[0] = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_1_GIF_SCALE);
        levelBackgroundGIF[1] = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_2_GIF_SCALE);
        levelBackgroundGIF[2] = LoadAndSave.GetGIF(LoadAndSave.LEVEL_ONE_BACKGROUND_GIF,  LoadAndSave.BACKGROUND_3_GIF_SCALE);
    }

    public void loadNextLevel(){
        levelIndex++;
        if(levelIndex>= levels.size()){
            levelIndex = 0;
            GameState.gameState = GameState.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyHandler().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getMaxLevelOffsetX());
        game.getPlaying().setMaxLevelOffsetY(newLevel.getMaxLevelOffsetY());
    }

    public void draw(Graphics g, int xLevelOffset, int yLevelOffset){
        g.setColor(new Color(25, 33, 40, 255));
        g.fillRect(0, 0, 1920, 1080);
        levelBackgroundGIF[levelIndex].paintIcon(null, g,
                levelIndex == 2 ? (int)(LoadAndSave.BACKGROUND_GIF_X_OFFSET[levelIndex]) : (int)(-xLevelOffset/LoadAndSave.BACKGROUND_GIF_X_OFFSET[levelIndex]),
                levelIndex == 0 ? (int)(-yLevelOffset/LoadAndSave.BACKGROUND_GIF_Y_OFFSET[levelIndex]) : (int)(LoadAndSave.BACKGROUND_GIF_Y_OFFSET[levelIndex]));
        g.drawImage(imgDesign[levelIndex], -xLevelOffset, -yLevelOffset,
                (int)(imgDesign[levelIndex].getWidth()* Game.SCALE),
                (int)(imgDesign[levelIndex].getHeight() * SCALE), null);
    }

    public void update(){

    }

    public Level getCurrentLevel(){
        return levels.get(levelIndex);
    }

    public int getLevelIndex(){
        return levelIndex;
    }

    public int getHowManyLevels(){
        return levels.size();
    }
}
