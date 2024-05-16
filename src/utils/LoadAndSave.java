package utils;

import entities.Boss;
import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Color;
import java.awt.Image;

import java.net.URL;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

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
    public static final String LEVEL_ONE_DESIGN = "/levels/long_level_design.png";
    public static final String LEVEL_ONE_BACKGROUND_GIF = "/levels/background.gif";

    public static final String MENU_BUTTONS = "/gui/buttons.png";
    public static final String GUI_BACKGROUND = "/gui/background.png";
    public static final String MENU_BACKGROUND = "/knight/Preview.gif";

    public static final String PAUSE_BUTTONS = "/gui/soundbuttons.png";
    public static final String CONTROL_BUTTONS = "/gui/controlbuttons.png";
    public static final String VOLUME_BUTTONS = "/gui/volumebuttons.png";
    public static final String PAUSE_BACKGROUND = "/gui/pause.png";

    public static final double MENU_GIF_SCALE = 2.04;
    public static final double BACKGROUND_GIF_SCALE = 0.66;

    // boss
    public static final String BOSS_BREATH = "/boss/breath.png";
    public static final String BOSS_BREATH_FIRE = "/boss/breath-fire.png";
    public static final String BOSS_ATTACK = "/boss/demon-attack.png";
    public static final String BOSS_ATTACK_NO_BREATH = "/boss/demon-attack-no-breath.png";
    public static final String BOSS_IDLE = "/boss/demon-idle.png";
    public static final String BOSS_NIGHTMARE_GALLOPING = "/boss/nightmare-galloping.png";
    public static final String BOSS_NIGHTMARE_IDLE= "/boss/nightmare-idle.png";



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

    public static ImageIcon GetGIF(String path, double scale){
        URL url = LoadAndSave.class.getResource(path);
        if (url == null) {
            System.err.println("Resource not found: " + path);
            return null;
        }
        ImageIcon gif = new ImageIcon(url);
        Image image = gif.getImage();
        Image newImage = image.getScaledInstance((int)(gif.getIconWidth() * Game.SCALE * scale), (int)(gif.getIconHeight() * Game.SCALE * scale), Image.SCALE_DEFAULT);
        return new ImageIcon(newImage);
    }


    public static ArrayList<Boss> GetBoss(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_HIT_BOXES);
        ArrayList<Boss> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                if(color.equals(Color.red)){
                    list.add(new Boss(i * Game.TILE_SIZE, j * Game.TILE_SIZE));
                }
            }
        }
        return list;
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
