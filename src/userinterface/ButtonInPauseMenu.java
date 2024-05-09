package userinterface;

import java.awt.*;

public class ButtonInPauseMenu {
    protected int xPosition, yPosition, buttonWidth, buttonHeight;
    protected Rectangle buttonHitBox;

    public ButtonInPauseMenu(int xPosition, int yPosition, int buttonWidth, int buttonHeight) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        initializeHitBoxButtons();
    }

    private void initializeHitBoxButtons() {
        buttonHitBox = new Rectangle(xPosition, yPosition, buttonWidth, buttonHeight);
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public Rectangle getButtonHitBox() {
        return buttonHitBox;
    }

    public void setButtonHitBox(Rectangle buttonHitBox) {
        this.buttonHitBox = buttonHitBox;
    }
}
