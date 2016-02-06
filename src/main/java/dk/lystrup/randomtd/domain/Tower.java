/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.TeslaTower;
import dk.lystrup.randomtd.ui.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

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
        fireTimer += deltaTime;
        if (fireTimer >= getFireRate()) {
            if (shootProjectile()) {
                fireTimer -= getFireRate();
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
        draw.drawImage(x, y, towerWidth, towerHeight, img);
    }

    /**
     * Attempt to fire a projectile.
     *
     * @return true if a projectile was fired, false otherwise
     */
    protected boolean shootProjectile() {
        //TODO need a way to find and target NPCs.
        Collection<Entity> targets = GamePanel.instance().getEntities();
        if (targets.isEmpty()) {
            return false;
        }
        //just some large number...
        double closestDistance = 99999;
        double dist;
        Entity closest = null;
        Vector2D selfVector = new Vector2D(x, y);
        for (Entity e : targets) {
            if (!(e instanceof NPC)) {
                continue;
            }
            dist = Vector2D.distance(selfVector, new Vector2D(e.getX(), e.getY()));
            if (dist < closestDistance) {
                closestDistance = dist;
                closest = e;
            }
        }
        if (closest != null) {
            if (closestDistance <= getRange()) {
                Projectile p = generateProjectile((NPC) closest);
                GamePanel.instance().addEntity(p);
                return true;
            }
        }
        return false;
    }

    protected abstract Projectile generateProjectile(NPC target);

    protected abstract double getDamage();

    protected abstract double getRange();
    
    protected abstract double getFireRate();

    protected abstract double getProjectileSpeed();
}
