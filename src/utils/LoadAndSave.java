package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
}
