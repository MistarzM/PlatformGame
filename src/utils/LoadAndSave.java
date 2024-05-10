package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Color;

public class LoadAndSave {

    public static final String KNIGHT_IDLE = "/knight/Idle.png";
    public static final String KNIGHT_CROUCH_IDLE = "/knight/crouch_idle.png";
    public static final String KNIGHT_RUN = "/knight/Run.png";
    public static final String KNIGHT_JUMP = "/knight/Jump.png";
    public static final String KNIGHT_HEALTH = "/knight/Health.png";
    public static final String KNIGHT_HURT = "/knight/Hurt.png";
    public static final String KNIGHT_DEATH = "/knight/Death.png";
    public static final String KNIGHT_CLIMB = "/knight/Climb.png";
    public static final String KNIGHT_HANGING = "/knight/Hanging.png";
    public static final String KNIGHT_SLIDE = "/knight/Slide.png";
    public static final String KNIGHT_ROLL = "/knight/Roll.png";
    public static final String KNIGHT_PRAY = "/knight/Pray.png";
    public static final String KNIGHT_ATTACKS = "/knight/Attacks.png";
    public static final String KNIGHT_AIR_ATTACK = "/knight/attack_from_air.png";
    public static final String KNIGHT_CROUCH_ATTACK = "/knight/crouch_attacks.png";

    public static final String MAIN_LEVEL = "/levels/main_lev_build.png";
    public static final String OTHER_AND_DECORATIVE = "/levels/other_and_decorative.png";
    //public static final String LEVEL_ONE_HIT_BOXES = "/levels/level_one_hitBoxes.png";
    public static final String LEVEL_ONE_HIT_BOXES = "/levels/long_level.png";
    //public static final String LEVEL_ONE_DESIGN = "/levels/level_one_design.png";
    public static final String LEVEL_ONE_DESIGN = "/levels/level_long_design.png";

    public static final String MENU_BUTTONS = "/gui/buttons.png";
    public static final String GUI_BACKGROUND = "/gui/background.png";

    public static final String PAUSE_BUTTONS = "/gui/soundbuttons.png";
    public static final String CONTROL_BUTTONS = "/gui/controlbuttons.png";
    public static final String VOLUME_BUTTONS = "/gui/volumebuttons.png";
    public static final String PAUSE_BACKGROUND = "/gui/pause.png";

    public static BufferedImage GetSpriteAtlas(String path){
        BufferedImage img = null;
        InputStream is = LoadAndSave.class.getResourceAsStream(path);
        try {
            img = ImageIO.read(is);

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelData(){

        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_HIT_BOXES);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = 1;
                if(color.equals(Color.BLACK) || color.equals(new Color(1,1,1))|| color.equals(new Color(4,4,4))|| color.equals(new Color(5,5,5))){
                    value = 0;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }
}
