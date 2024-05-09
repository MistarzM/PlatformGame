package userinterface;

import utils.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.GUI.PauseButtons.*;

public class SoundButton extends ButtonInPauseMenu{
    private BufferedImage[][] soundImages;
    private boolean mouseHover, mousePressed;
    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int xPosition, int yPosition, int buttonWidth, int buttonHeight) {
        super(xPosition, yPosition, buttonWidth, buttonHeight);

        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadAndSave.GetSpriteAtlas(LoadAndSave.PAUSE_BUTTONS);
        soundImages = new BufferedImage[2][3];
        for(int i = 0 ; i < soundImages.length; i++) {
            for(int j = 0 ; j < soundImages[i].length; j++) {
                soundImages[i][j] = temp.getSubimage(j * SOUND_SIZE, i * SOUND_SIZE, SOUND_SIZE, SOUND_SIZE);
            }
        }
    }

    public void update() {
        if(muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if(mouseHover) {
            colIndex = 1;
        }
        if(mousePressed) {
            colIndex = 2;
        }

    }
    public void resetButtons() {
        mouseHover = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImages[rowIndex][colIndex], xPosition, yPosition, buttonWidth, buttonHeight, null);
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseHover() {
        return mouseHover;
    }

    public void setMouseHover(boolean mouseHover) {
        this.mouseHover = mouseHover;
    }
}
