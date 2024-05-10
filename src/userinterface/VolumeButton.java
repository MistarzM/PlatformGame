package userinterface;

import utils.Constants;
import utils.LoadAndSave;
import static utils.Constants.GUI.VolumeButtons.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class VolumeButton extends ButtonInPauseMenu{

    private BufferedImage[] images;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseHover, mousePressed;
    private int sliderX, minimumX, maximumX;

    public VolumeButton(int xPosition, int yPosition, int buttonWidth, int buttonHeight) {
        super(xPosition + buttonWidth / 2, yPosition, VOLUME_WIDTH, buttonHeight);
        buttonHitBox.x -= SCALED_VOLUME_WIDTH / 2;
        sliderX = xPosition + buttonWidth / 2;
        this.xPosition = xPosition;
        this.buttonWidth = buttonWidth;
        minimumX = xPosition + SCALED_VOLUME_WIDTH / 2;
        maximumX = xPosition + buttonWidth - SCALED_VOLUME_WIDTH / 2;
        loadVolumeImages();
    }

    private void loadVolumeImages() {
        BufferedImage temp = LoadAndSave.GetSpriteAtlas(LoadAndSave.VOLUME_BUTTONS);
        images = new BufferedImage[3];
        for(int i = 0 ; i < images.length ; i++) {
            images[i] = temp.getSubimage(i * VOLUME_WIDTH, 0, VOLUME_WIDTH, VOLUME_HEIGHT);
        }
        slider = temp.getSubimage(3* VOLUME_WIDTH,0, SLIDER_WIDTH, VOLUME_HEIGHT);

    }

    public void update() {
        index = 0;
        if(mouseHover)
        {
            index = 1;
        }
        if(mousePressed)
        {
            index = 2;
        }
    }

    public void resetButtons() {
        mouseHover = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(slider, xPosition, yPosition, buttonWidth, buttonHeight, null);
        g.drawImage(images[index], sliderX - SCALED_VOLUME_WIDTH / 2, yPosition, VOLUME_WIDTH, buttonHeight, null);
    }

    public void changeX(int x) {
        if( x < minimumX) {
            sliderX = minimumX;
        } else if ( x > maximumX) {
            sliderX = maximumX;
        } else {
            sliderX = x;
        }
        buttonHitBox.x = sliderX - SCALED_VOLUME_WIDTH / 2;

    }

    public boolean isMouseHover() {
        return mouseHover;
    }

    public void setMouseHover(boolean mouseHover) {
        this.mouseHover = mouseHover;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
