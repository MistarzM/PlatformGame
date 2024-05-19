package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    protected int animationTick, animationIndex;
    protected int state;
    protected float runningSpeed;
    protected float speedInAir;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;


    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int)(height * Game.SCALE));
    }

    //protected void updateHitBox(){
    //    hitBox.x = (int) x;
    //    hitBox.y = (int) y;
    //}

    public Rectangle2D.Float getHitBox(){
        return hitBox;
    }

    protected void drawHitBox(Graphics graphics, int xLevelOffset){
        // for testing hit boxes
        graphics.setColor(Color.red);
        graphics.drawRect((int)hitBox.x - xLevelOffset, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);
    }

    public int getState(){
        return state;
    }

    public int getAnimationIndex(){
        return animationIndex;
    }
}
