package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadAndSave {

    public static BufferedImage GetPlayerAtlas(String path){
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
