package entities;

import gamestates.Playing;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyHandler {

    private Playing playing;
    private BufferedImage[][] bossAnimations;
    private ArrayList<Boss> bosses = new ArrayList<>();

    public EnemyHandler(Playing playing){
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        bosses = LoadAndSave.GetBoss();
        System.out.println("size of bosses: " + bosses.size());
    }

    public void update(int[][] levelData){
        for(Boss b : bosses){
            b.update(levelData);
        }
    }

    public void draw(Graphics g, int xLevelOffset){
        drawBoss(g, xLevelOffset);
        drawHitBox(g, xLevelOffset);
    }

    private void drawBoss(Graphics g, int xLevelOffset) {
        for(Boss b : bosses){
            g.drawImage(bossAnimations[b.getEnemyState()][b.getAnimationIndex()], (int)b.getHitBox().x - xLevelOffset - BOSS_DRAW_OFFSET_X, (int)b.getHitBox().y - BOSS_DRAW_OFFSET_Y , BOSS_WIDTH, BOSS_HEIGHT,null);
        }
    }
    protected void drawHitBox(Graphics graphics, int xLevelOffset){
        // for testing hit boxes
        graphics.setColor(Color.red);
        for(Boss b : bosses) {
            graphics.drawRect((int)b.getHitBox().x - xLevelOffset, (int)b.getHitBox().y, (int)b.getHitBox().width, (int)b.getHitBox().height);
        }
    }

    private void loadEnemyImages() {
        bossAnimations = new BufferedImage[4][11];

        BufferedImage temp_idle = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_IDLE);
        BufferedImage temp_attack = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK);
        BufferedImage temp_attack_no_breath = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK_NO_BREATH);

        for(int i = 0; i < bossAnimations[0].length; i++){
            if(i<6){
                bossAnimations[IDLE][i] = temp_idle.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
                bossAnimations[RUNNING][i] = temp_idle.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i<11){
                bossAnimations[ATTACK][i] = temp_attack.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i < 8){
                bossAnimations[ATTACK_NO_BREATH][i] = temp_attack_no_breath.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
        }
    }
}
