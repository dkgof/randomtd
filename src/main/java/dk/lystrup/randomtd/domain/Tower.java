/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.TeslaTower;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.util.EntityUtil;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author rolf
 */
public abstract class Tower extends Entity {

    private double towerWidth, towerHeight;
    private double fireTimer;
    private String imagePath;

    protected int level;
    private BufferedImage img;

    public Tower(double x, double y, double towerWidth, double towerHeight, String imagePath) {
        super(x, y);
        this.towerWidth = towerWidth;
        this.towerHeight = towerHeight;
        this.imagePath = imagePath;

        this.fireTimer = 0;
        this.level = 0;
    }

    public Tower(double x, double y) {
        super(x, y);
        level = 0;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if(getFireRate() == 0){
            return;
        }
        fireTimer += deltaTime;
        if (fireTimer >= getFireRate()) {
            if (shootProjectile()) {
                fireTimer -= getFireRate();
            }else{
                fireTimer = getFireRate();
            }
        }
    }

    @Override
    public void draw(DrawHelper draw) {
        if (img == null) {
            try {
                img = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imagePath));
            } catch (IOException ex) {
                Logger.getLogger(TeslaTower.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        draw.drawImage(x, y, towerWidth, towerHeight, img, 0);
    }

    /**
     * Attempt to fire a projectile.
     *
     * @return true if a projectile was fired, false otherwise
     */
    protected boolean shootProjectile() {
        //TODO need a way to find and target NPCs.
        NPC closest = (NPC) EntityUtil.closestEntityOfType(x, y, getRange(), NPC.class, null);
        if (closest != null) {
            Projectile p = generateProjectile((NPC) closest);
            GamePanel.instance().addEntity(p);
            return true;
        }
        return false;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void levelUp() {
        this.level++;
    }

    protected abstract Projectile generateProjectile(NPC target);

    protected abstract double getDamage();

    protected abstract double getRange();

    protected abstract double getFireRate();

    protected abstract double getProjectileSpeed();
    
    protected double getSplashRadius(){
        return 0;
    }
    
    protected double getMinSplashFactor(){
        return 0;
    }
}
