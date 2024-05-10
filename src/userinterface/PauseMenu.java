package userinterface;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;
import static utils.Constants.GUI.PauseButtons.*;
import static utils.Constants.GUI.ControlButtons.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class PauseMenu {

    private Playing playing;
    private BufferedImage backgroundPauseImage;
    private int pauseXPosition, pauseYPosition, pauseWidth, pauseHeight;
    private SoundButton musicButton, sfxButton;
    private ControlButton menuButton, replayButton, unpauseButton;

    public PauseMenu(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createControlButtons();
    }

    private void createControlButtons() {
        int menuX = (int)(500 * Game.SCALE);
        int replayX = (int)(600 * Game.SCALE);
        int unpauseX = (int)(700 * Game.SCALE);
        int controlY =  (int)(500 * Game.SCALE);

        unpauseButton = new ControlButton(unpauseX, controlY, SCALED_CONTROL_SIZE, SCALED_CONTROL_SIZE, 0);
        replayButton = new ControlButton(replayX, controlY, SCALED_CONTROL_SIZE, SCALED_CONTROL_SIZE, 1);
        menuButton = new ControlButton(menuX, controlY, SCALED_CONTROL_SIZE, SCALED_CONTROL_SIZE, 2);
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

        unpauseButton.update();
        replayButton.update();
        menuButton.update();
    }

    public void draw(Graphics g) {
        //Pause Menu
        g.drawImage(backgroundPauseImage, pauseXPosition, pauseYPosition, pauseWidth, pauseHeight, null);

        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Control buttons
        unpauseButton.draw(g);
        replayButton.draw(g);
        menuButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        } else if (isIn(e, replayButton)) {
            replayButton.setMousePressed(true);
        } else if (isIn(e, menuButton)) {
            menuButton.setMousePressed(true);
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
        } else if (isIn(e, unpauseButton)) {
            if (unpauseButton.isMousePressed()) {
                playing.unpauseGame();
            };
        } else if (isIn(e, replayButton)) {
            if (replayButton.isMousePressed()) {
                System.out.println("Replay level");
            };
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                GameState.gameState = GameState.MENU;
                playing.unpauseGame();
            };
        }


        musicButton.resetButtons();
        sfxButton.resetButtons();
        unpauseButton.resetButtons();
        replayButton.resetButtons();
        menuButton.resetButtons();
    }

    public void mouseMoves(MouseEvent e) {
        musicButton.setMouseHover(false);
        sfxButton.setMouseHover(false);

        unpauseButton.setMouseHover(false);
        replayButton.setMouseHover(false);
        menuButton.setMouseHover(false);

        if(isIn(e, musicButton)) {
            musicButton.setMouseHover(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseHover(true);
        } else if (isIn(e, unpauseButton)) {
            unpauseButton.setMouseHover(true);
        }
        else if (isIn(e, replayButton)) {
            replayButton.setMouseHover(true);
        }
        else if (isIn(e, menuButton)) {
            menuButton.setMouseHover(true);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, ButtonInPauseMenu b) {
        return b.getButtonHitBox().contains(e.getX(), e.getY());
    }
}
