package userinterface;

import utils.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.GUI.ControlButtons.*;

public class ControlButton extends ButtonInPauseMenu{
    private BufferedImage[] images;
    private int rowIndex, index;
    private boolean mouseHover, mousePressed;

    public ControlButton(int xPosition, int yPosition, int buttonWidth, int buttonHeight, int rowIndex) {
        super(xPosition, yPosition, buttonWidth, buttonHeight);
        this.rowIndex = rowIndex;
        loadControlImages();
    }

    private void loadControlImages() {
        BufferedImage temp = LoadAndSave.GetSpriteAtlas(LoadAndSave.CONTROL_BUTTONS);
        images = new BufferedImage[3];
        for(int i = 0 ; i < images.length ; i++)
        {
            images[i] = temp.getSubimage(i * CONTROL_SIZE, rowIndex * CONTROL_SIZE, CONTROL_SIZE, CONTROL_SIZE);
        }
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
        g.drawImage(images[index], xPosition, yPosition, CONTROL_SIZE, CONTROL_SIZE, null);
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
