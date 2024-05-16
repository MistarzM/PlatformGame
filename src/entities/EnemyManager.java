package entities;

import gamestates.Playing;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] bossAnimations;
    private ArrayList<Boss> bosses = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImages();
    }

    public void update(){
        for(Boss b : bosses){
            b.update();
        }
    }

    public void draw(Graphics g){
        drawBoss(g);
    }

    private void drawBoss(Graphics g) {
        for(Boss b : bosses){
            g.drawImage(bossAnimations[b.getEnemyState()][b.getAnimationIndex()], (int)(b.getHitBox().x), (int)(b.getHitBox().y), BOSS_WIDTH, BOSS_HEIGHT,null);
        }
    }

    private void loadEnemyImages() {
        bossAnimations = new BufferedImage[3][11];

        BufferedImage temp_idle = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_IDLE);
        BufferedImage temp_attack = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK);
        BufferedImage temp_attack_no_breath = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK_NO_BREATH);

        for(int i = 0; i < bossAnimations.length; i++){
            if(i<6){
                bossAnimations[IDLE][i] = temp_idle.getSubimage(i*BOSS_WIDTH_DEFAULT, i*BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i<11){
                bossAnimations[ATTACK][i] = temp_attack.getSubimage(i*BOSS_WIDTH_DEFAULT, i*BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i < 8){
                bossAnimations[ATTACK_NO_BREATH][i] = temp_attack_no_breath.getSubimage(i*BOSS_WIDTH_DEFAULT, i*BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
        }
    }
}
