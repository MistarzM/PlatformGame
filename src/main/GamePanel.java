package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;



public class GamePanel extends JPanel {     // JPanel -> picture

    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game){
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    public void updateGame(){
    }

    public void paintComponent(Graphics g){     // Graphics -> you need this to draw
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }

}
