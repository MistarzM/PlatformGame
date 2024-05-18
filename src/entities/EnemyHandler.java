package entities;

import gamestates.Playing;
import utils.LoadAndSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyHandler {

    private Playing playing;
    private BufferedImage[][] bossAnimations;
    private BufferedImage[][] skeletonSwordAnimations;
    private ArrayList<Boss> bosses = new ArrayList<>();
    private ArrayList<SkeletonSword> skeletonsSword = new ArrayList<>();

    public EnemyHandler(Playing playing){
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        bosses = LoadAndSave.GetBoss();
        System.out.println("size of bosses: " + bosses.size());

        skeletonsSword = LoadAndSave.GetSkeletonSword();
        System.out.println("size of skeleton: " + skeletonsSword.size());
    }

    public void update(int[][] levelData, Player player){
        for(Boss b : bosses){
            b.update(levelData, player);
        }
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive()) {
                s.update(levelData, player);
            }
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
    }

    public void draw(Graphics g, int xLevelOffset){
        drawBoss(g, xLevelOffset);
        drawSkeletonSword(g, xLevelOffset);
        drawHitBox(g, xLevelOffset);
    }

    private void drawBoss(Graphics g, int xLevelOffset) {
        for(Boss b : bosses){
            g.drawImage(bossAnimations[b.getEnemyState()][b.getAnimationIndex()], (int)b.getHitBox().x - xLevelOffset - BOSS_DRAW_OFFSET_X + b.flipX(), (int)b.getHitBox().y - BOSS_DRAW_OFFSET_Y , BOSS_WIDTH * b.flipW(), BOSS_HEIGHT,null);
        }
    }

    private void drawSkeletonSword(Graphics g, int xLevelOffset){
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive())
                g.drawImage(skeletonSwordAnimations[s.getEnemyState()][s.getAnimationIndex()],(int)s.getHitBox().x - xLevelOffset - SKELETON_SWORD_DRAW_OFFSET_X + s.flipX(), (int)s.getHitBox().y - SKELETON_SWORD_DRAW_OFFSET_Y , SKELETON_SWORD_WIDTH * s.flipW(), SKELETON_SWORD_HEIGHT,null );
        }
    }

    protected void drawHitBox(Graphics graphics, int xLevelOffset){
        // for testing hit boxes
        graphics.setColor(Color.red);
        for(Boss b : bosses) {
            graphics.drawRect((int)b.getHitBox().x - xLevelOffset, (int)b.getHitBox().y, (int)b.getHitBox().width, (int)b.getHitBox().height);
        }
        for(SkeletonSword s : skeletonsSword){
            if(s.isAlive()) {
                graphics.drawRect((int) s.getHitBox().x - xLevelOffset, (int) s.getHitBox().y, (int) s.getHitBox().width, (int) s.getHitBox().height);
                s.drawAttackHitBox(graphics, xLevelOffset);
            }
        }
    }

    private void loadEnemyImages() {
        bossAnimations = new BufferedImage[4][11];

        BufferedImage temp_boss_idle = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_IDLE);
        BufferedImage temp_boss_attack = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK);
        BufferedImage temp_boss_attack_no_breath = LoadAndSave.GetSpriteAtlas(LoadAndSave.BOSS_ATTACK_NO_BREATH);

        for(int i = 0; i < bossAnimations[0].length; i++){
            if(i<6){
                bossAnimations[BOSS_IDLE][i] = temp_boss_idle.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
                bossAnimations[BOSS_FLYING][i] = temp_boss_idle.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
            }
            if(i<11){
                bossAnimations[BOSS_ATTACK][i] = temp_boss_attack.getSubimage(i*240, 0, 240,192);
            }
            if(i < 8){
                bossAnimations[BOSS_ATTACK_NO_BREATH][i] = temp_boss_attack_no_breath.getSubimage(i*BOSS_WIDTH_DEFAULT, 0, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
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
}
