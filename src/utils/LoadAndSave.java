package utils;

import entities.Boss;
import entities.SkeletonSword;
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

    public static final String HEALTH_BAR_MINIMUM_DAMAGE = "/gui/MinimumDamage-Sheet.png";
    // level
    // -> to design new levels
    public static final String MAIN_LEVEL = "/levels/to_design_new_levels/main_lev_build.png";
    public static final String OTHER_AND_DECORATIVE = "/levels/to_design_new_levels/other_and_decorative.png";

    // -> hit boxes and design
    public static final String LEVEL_ONE_HIT_BOXES = "/levels/level_1_hitBox.png";
    public static final String LEVEL_ONE_DESIGN = "/levels/level_1_design.png";
    public static final String LEVEL_ONE_BACKGROUND_GIF = "/levels/background.gif";
    public static final String LEVEL_TWO_HIT_BOXES = "/levels/level_2_hitBox.png";
    public static final String LEVEL_TWO_DESIGN = "/levels/level_2_design.png";

    // user interface
    public static final String MENU_BUTTONS = "/gui/buttons.png";
    public static final String GUI_BACKGROUND = "/gui/background.png";
    public static final String MENU_BACKGROUND = "/knight/Preview.gif";

    public static final String PAUSE_BUTTONS = "/gui/soundbuttons.png";
    public static final String CONTROL_BUTTONS = "/gui/controlbuttons.png";
    public static final String VOLUME_BUTTONS = "/gui/volumebuttons.png";
    public static final String PAUSE_BACKGROUND = "/gui/pause.png";

    public static final String KEYBOARD_BUTTONS = "/gui/Keyboard_Letters_and_Symbols.png";

    // GIF - background (menu and game)
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

    //skeleton_sword
    public static final String[] SKELETON_SWORD_ATTACK1 = {"/skeleton_sword/attack-A1.png", "/skeleton_sword/attack-A2.png", "/skeleton_sword/attack-A3.png", "/skeleton_sword/attack-A4.png","/skeleton_sword/attack-A5.png", "/skeleton_sword/attack-A6.png", "/skeleton_sword/attack-A7.png", "/skeleton_sword/attack-A8.png"};
    public static final String[] SKELETON_SWORD_ATTACK2 = {"/skeleton_sword/attack-B1.png", "/skeleton_sword/attack-B2.png", "/skeleton_sword/attack-B3.png", "/skeleton_sword/attack-B4.png","/skeleton_sword/attack-B5.png", "/skeleton_sword/attack-B6.png", "/skeleton_sword/attack-B7.png", "/skeleton_sword/attack-B8.png", "/skeleton_sword/attack-B9.png", "/skeleton_sword/attack-B10.png", "/skeleton_sword/attack-B11.png"};
    public static final String[] SKELETON_SWORD_DEAD = {"/skeleton_sword/dead-1.png", "/skeleton_sword/dead-2.png","/skeleton_sword/dead-3.png","/skeleton_sword/dead-4.png"};
    public static final String[] SKELETON_SWORD_HIT =  {"/skeleton_sword/hit-1.png", "/skeleton_sword/hit-2.png","/skeleton_sword/hit-3.png"};
    public static final String[] SKELETON_SWORD_IDLE = {"/skeleton_sword/idle-1.png", "/skeleton_sword/idle-2.png","/skeleton_sword/idle-3.png","/skeleton_sword/idle-4.png"};
    public static final String[] SKELETON_SWORD_JUMP = {"/skeleton_sword/jump-1.png", "/skeleton_sword/jump-2.png","/skeleton_sword/jump-3.png","/skeleton_sword/jump-4.png", "/skeleton_sword/jump-5.png","/skeleton_sword/jump-6.png"};
    public static final String[] SKELETON_SWORD_WALK = {"/skeleton_sword/walk-1.png", "/skeleton_sword/walk-2.png","/skeleton_sword/walk-3.png","/skeleton_sword/walk-4.png", "/skeleton_sword/walk-5.png","/skeleton_sword/walk-6.png"};

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

    public static BufferedImage[] GetSpriteAtlas(String[] path){
        BufferedImage[] img = new BufferedImage[path.length];
        for(int i = 0; i < path.length; i++){
            InputStream is = LoadAndSave.class.getResourceAsStream(path[i]);
            try{
                img[i] = ImageIO.read(is);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public static ArrayList<SkeletonSword> GetSkeletonSword(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_HIT_BOXES);
        ArrayList<SkeletonSword> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                if(color.equals(Color.green)){
                    list.add(new SkeletonSword(i * Game.TILE_SIZE, j * Game.TILE_SIZE));
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
                if(color.equals(new Color(128, 128, 128))){
                    value = 2;
                }
                levelData[j][i] = value;
            }
        }
        return levelData;
    }
}
