package userinterface;

import gamestates.GameState;
import main.Game;
import utils.LoadAndSave;
import static utils.Constants.GUI.PauseButtons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseMenu {

    private BufferedImage backgroundPauseImage;
    private int pauseXPosition, pauseYPosition, pauseWidth, pauseHeight;
    private SoundButton musicButton, sfxButton;

    public PauseMenu() {
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() {
        int soundX = (Game.PANEL_WIDTH / 2 - pauseWidth / 2) + (int)(250 * Game.SCALE);
        int musicY = (int)(180 * Game.SCALE);
        int sfxY = (int)(270 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SCALED_SOUND_SIZE, SCALED_SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SCALED_SOUND_SIZE, SCALED_SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundPauseImage = LoadAndSave.GetSpriteAtlas(LoadAndSave.PAUSE_BACKGROUND);

        pauseWidth = (int)(backgroundPauseImage.getWidth() * Game.SCALE * 3);
        pauseHeight = (int)(backgroundPauseImage.getHeight() * Game.SCALE * 3);

        pauseXPosition = Game.PANEL_WIDTH / 2 - pauseWidth / 2;
        pauseYPosition = (int) ( 130 * Game.SCALE); //check later

    }

    public void update() {
        musicButton.update();
        sfxButton.update();
    }

    public void draw(Graphics g) {
        //Pause Menu
        g.drawImage(backgroundPauseImage, pauseXPosition, pauseYPosition, pauseWidth, pauseHeight, null);

        //Pause buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            };
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            };
        }

        musicButton.resetButtons();
        sfxButton.resetButtons();
    }

    public void mouseMoves(MouseEvent e) {
        musicButton.setMouseHover(false);
        sfxButton.setMouseHover(false);

        if(isIn(e, musicButton)) {
            musicButton.setMouseHover(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseHover(true);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, ButtonInPauseMenu b) {
        return b.getButtonHitBox().contains(e.getX(), e.getY());
    }
}
