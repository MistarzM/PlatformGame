package userinterface;

import gamestates.GameState;
import utils.LoadAndSave;
import static utils.Constants.GUI.Buttons.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ButtonInMenu {

    private int xPosition, yPosition, imageRow, index;
    private GameState gameState;
    private BufferedImage[] images;
    private boolean mouseHover, mousePressed;
    private Rectangle buttonHitBox;

    public ButtonInMenu(int xPosition, int yPosition, int imageRow, GameState gameState){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.imageRow = imageRow;
        this.gameState = gameState;
        loadImages();
        initializeHitBoxButtons();
    }

    private void initializeHitBoxButtons(){
        buttonHitBox = new Rectangle(xPosition - SCALED_BUTTONS_WIDTH/2, yPosition, BUTTONS_WIDTH, BUTTONS_HEIGHT);
    }

    private void loadImages(){
        images = new BufferedImage[3];

        BufferedImage buttonsImg = LoadAndSave.GetSpriteAtlas(LoadAndSave.MENU_BUTTONS);

        for(int i = 0; i < images.length; i++){
            images[i] = buttonsImg.getSubimage(i * BUTTONS_WIDTH, imageRow * BUTTONS_HEIGHT, SCALED_BUTTONS_WIDTH, SCALED_BUTTONS_HEIGHT);
        }
    }

    public void draw(Graphics g){
        g.drawImage(images[index], xPosition - SCALED_BUTTONS_WIDTH/2, yPosition, BUTTONS_WIDTH, BUTTONS_HEIGHT, null);
    }

    public void update(){
        index = 0;
        if(mouseHover){
            index = 1;
        }
        if(mousePressed){
            index = 2;
        }
    }

    public boolean getMouseHover(){
        return mouseHover;
    }
    public boolean getMousePressed(){
        return mousePressed;
    }

    public void setMouseHover(boolean mouseHover){
        this.mouseHover = mouseHover;
    }
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public Rectangle getButtonHitBox(){
        return buttonHitBox;
    }

    public void setGameState(){
        GameState.gameState = gameState;
    }

    public void resetMenuButtons(){
        mouseHover = false;
        mousePressed = false;
    }
}
