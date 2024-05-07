package gamestates;

import main.Game;
import userinterface.ButtonInMenu;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;
    public State(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }

    public boolean cursorOnButton(MouseEvent e, ButtonInMenu b){
        return b.getButtonHitBox().contains(e.getX(), e.getY());
    }
}
