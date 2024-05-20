package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import levels.*;
import static utils.Constants.EnemyConstants.*;

public class EnemyHandler {

    private Playing playing;
    private BufferedImage[][] bossAnimations;
    private BufferedImage[][] skeletonSwordAnimations;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<SkeletonSword> skeletonsSword = new ArrayList<>();
    private boolean defeatBoss;

    public EnemyHandler(Playing playing){
        this.playing = playing;
        loadEnemyImages();
    }

    public void loadEnemies(Level level) {
        bosses = level.getBosses();
        System.out.println("size of bosses: " + bosses.size());

        skeletonsSword = level.getSkeletonsSword();
        System.out.println("size of skeleton: " + skeletonsSword.size());
    }

    public void update(int[][] levelData, Player player){

        defeatBoss = true;
        for(Boss b : bosses){
            if(b.isAlive()) {
                b.update(levelData, player);
                defeatBoss = false;
            }
        }
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive()) {
                s.update(levelData, player);
            }
        }
        if(playing.getLevelHandler().getLevelIndex() == 1 && defeatBoss){
            playing.setIsVictory(true);
        }
    }

    public void checkEnemyHurt(Rectangle2D.Float attackHitBox){
        for(SkeletonSword s :  skeletonsSword){
            if(s.isAlive()) {
                if (attackHitBox.intersects(s.getHitBox())) {
                    s.hurt(1);      //damage player->enemy
                    return;
                }
            }
        }
        for(Boss b : bosses){
            if(b.isAlive()){
                if(attackHitBox.intersects(b.getHitBox())){
                    b.hurt(1);
                    return;
                }
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset, int yLevelOffset){
        drawBoss(g, xLevelOffset, yLevelOffset);
        drawSkeletonSword(g, xLevelOffset, yLevelOffset);
        drawHitBox(g, xLevelOffset, yLevelOffset);
    }

    private void drawBoss(Graphics g, int xLevelOffset, int yLevelOffset) {
        for(Boss b : bosses) {
            if (b.isAlive()) {
                g.drawImage(bossAnimations[b.getState()][b.getAnimationIndex()],
                        (int) b.getHitBox().x - xLevelOffset - BOSS_DRAW_OFFSET_X + b.flipX(),
                        (int) b.getHitBox().y- yLevelOffset - BOSS_DRAW_OFFSET_Y,
                        BOSS_WIDTH * b.flipW(), BOSS_HEIGHT, null);
            }
        }
    }

    private void drawSkeletonSword(Graphics g, int xLevelOffset, int yLevelOffset){
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive())
                g.drawImage(skeletonSwordAnimations[s.getState()][s.getAnimationIndex()],(int)s.getHitBox().x - xLevelOffset - SKELETON_SWORD_DRAW_OFFSET_X + s.flipX(), (int)s.getHitBox().y- yLevelOffset - SKELETON_SWORD_DRAW_OFFSET_Y , SKELETON_SWORD_WIDTH * s.flipW(), SKELETON_SWORD_HEIGHT,null );
        }
    }

    protected void drawHitBox(Graphics graphics, int xLevelOffset, int yLevelOffset){
        // for testing hit boxes
        graphics.setColor(Color.red);
        for(Boss b : bosses) {
            if(b.isAlive()) {
                graphics.drawRect((int) b.getHitBox().x - xLevelOffset, (int) b.getHitBox().y-yLevelOffset, (int) b.getHitBox().width, (int) b.getHitBox().height);
                b.drawAttackHitBox(graphics, xLevelOffset, yLevelOffset);
            }
        }
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive()) {
                graphics.drawRect((int) s.getHitBox().x - xLevelOffset, (int) s.getHitBox().y - yLevelOffset, (int) s.getHitBox().width, (int) s.getHitBox().height);
                s.drawAttackHitBox(graphics, xLevelOffset, yLevelOffset);
            }
        }
    }

    private void loadEnemyImages() {
        bossAnimations = new BufferedImage[7][16];

        BufferedImage temp_boss = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS);

        for(int i = 0; i < bossAnimations[0].length; i++){
            if(i<8){
                bossAnimations[BOSS_IDLE][i] = temp_boss.getSubimage((i%8)*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
                bossAnimations[BOSS_WALK][i] = temp_boss.getSubimage((i%8)*BOSS_WIDTH_DEFAULT, (i + 8)/8 * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i<10){
                bossAnimations[BOSS_ATTACK][i] = temp_boss.getSubimage((i%8)*BOSS_WIDTH_DEFAULT, (i + 16)/8 * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT,BOSS_HEIGHT_DEFAULT);
                bossAnimations[BOSS_DEAD][i] = temp_boss.getSubimage((i + 29)%8*BOSS_WIDTH_DEFAULT, (i+29)/8 * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT,BOSS_HEIGHT_DEFAULT);
            }
            if(i < 3){
                bossAnimations[BOSS_HURT][i] = temp_boss.getSubimage((i+26)%8*BOSS_WIDTH_DEFAULT, (i+26)/8 * BOSS_HEIGHT_DEFAULT , BOSS_WIDTH_DEFAULT,BOSS_HEIGHT_DEFAULT);
            }
            if (i < 9) {
                bossAnimations[BOSS_CAST][i] = temp_boss.getSubimage((i+39)%8*BOSS_WIDTH_DEFAULT, (i+39)/8 * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT,BOSS_HEIGHT_DEFAULT);
            }
            if(i<16){
                bossAnimations[BOSS_SPELL][i] = temp_boss.getSubimage((i+48)%8*BOSS_WIDTH_DEFAULT, (i+48)/8 * BOSS_HEIGHT_DEFAULT, BOSS_WIDTH_DEFAULT,BOSS_HEIGHT_DEFAULT);
            }
        }

        skeletonSwordAnimations = new BufferedImage[7][11];

        BufferedImage[] temp_skeleton_sword_idle = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_IDLE);
        BufferedImage[] temp_skeleton_sword_walk = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_WALK);
        BufferedImage[] temp_skeleton_sword_jump = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_JUMP);
        BufferedImage[] temp_skeleton_sword_hit = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_HIT);
        BufferedImage[] temp_skeleton_sword_dead = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_DEAD);
        BufferedImage[] temp_skeleton_sword_attack1 = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_ATTACK1);
        BufferedImage[] temp_skeleton_sword_attack2 = LoadAndSave.GetSpriteAtlas(LoadAndSave.SKELETON_SWORD_ATTACK2);

        for(int i = 0; i < skeletonSwordAnimations[0].length; i++){
            if(i < 4){
                skeletonSwordAnimations[SKELETON_SWORD_IDLE][i] = temp_skeleton_sword_idle[i];
                skeletonSwordAnimations[SKELETON_SWORD_DEAD][i] = temp_skeleton_sword_dead[i];
            }
            if(i< 6){
                skeletonSwordAnimations[SKELETON_SWORD_JUMP][i] = temp_skeleton_sword_jump[i];
                skeletonSwordAnimations[SKELETON_SWORD_WALK][i] = temp_skeleton_sword_walk[i];
            }
            if(i < 3){
                skeletonSwordAnimations[SKELETON_SWORD_HIT][i] = temp_skeleton_sword_hit[i];
            }
            if(i<8){
                skeletonSwordAnimations[SKELETON_SWORD_ATTACK_1][i] = temp_skeleton_sword_attack1[i];
            }
            if(i<11){
                skeletonSwordAnimations[SKELETON_SWORD_ATTACK_2][i] = temp_skeleton_sword_attack2[i];
            }
        }

    }

    public void resetEnemies(){
     for(SkeletonSword s : skeletonsSword){
         s.resetEnemy();
     }
     for(Boss b : bosses){
         b.resetEnemy();
     }
    }

}
