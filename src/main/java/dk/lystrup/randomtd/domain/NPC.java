/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.domain.Projectile.DamageType;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.TeslaTower;
import dk.lystrup.randomtd.ui.GamePanel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author rolf
 */
public abstract class NPC extends Entity {
    
    private static final double HEALTH_BAR_OFFSET = 1.5;
    private static final double HEALTH_BAR_HEIGHT = 1;
    private static final double MIN_HEALTH_BAR_WIDTH = 3;

    /**
     * Declare new armor types last.
     */
    public enum ArmorType {

        NONE,
        LIGHT,
        MEDIUM,
        HEAVY
    }

    private BufferedImage img;
    private boolean alive;
    private final String imagePath;
    private final double width, height;
       
    protected double maxHealth;

    protected double currentHealth;

    protected double armor;

    protected ArmorType armorType;

    protected double movementSpeed;

    protected boolean flying;

    private ArrayList<Vector2D> path;
    private int pathIndex;
    
    private Vector2D direction;
    private final double speed;
    
    public NPC(double x, double y, double width, double height, String imagePath, double speed) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        direction = new Vector2D(0,0);
        this.speed = speed;
        pathIndex = 0;
    }

    /**
     * Deal damage to this target
     * @param damageDealer damage dealer, used to apply buff modifiers.
     * @param type Damage type of the damage to be dealt.
     * @param amount amount of damage to be dealt (before buffs)
     * @return true if the target is alive after taking damage
     */
    public void doDamage(Entity damageDealer, DamageType type, double amount) {
        double dmg = (amount - armor) * type.getDamageVersus(armorType);
        //buffs changing damage done and taken are applied after reductions
        dmg = damageDealer.onDamageDone(dmg);
        dmg = onDamageTaken(dmg);
        if (dmg > 0) {
            currentHealth -= dmg;
            if(currentHealth <= 0){
                GamePanel.instance().removeEntity(this);
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
        draw.drawImage(x, y, width, height, img, getAngle());
        double healthBarWidth = Math.max(MIN_HEALTH_BAR_WIDTH, width);
        double healthFactor = currentHealth / maxHealth;
        double adjustedWidth = healthFactor*healthBarWidth;
        draw.fillRectangle(x-healthBarWidth/2 + adjustedWidth/2, y-HEALTH_BAR_OFFSET, adjustedWidth, HEALTH_BAR_HEIGHT, Color.green);
        draw.drawRectangle(x, y-HEALTH_BAR_OFFSET, healthBarWidth, HEALTH_BAR_HEIGHT, Color.black);
    }
    
    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        if(path == null) {
            path = GamePanel.instance().getNpcPath();
        }
        
        if(path != null) {
            Vector2D currentTarget = path.get(pathIndex);
            Vector2D currentPos = new Vector2D(x, y);
            
            if(currentTarget.distance(currentPos) < 0.5) {
                pathIndex++;
                
                if(pathIndex >= path.size()) {
                    //Reached last point, we are at despawn?
                    GamePanel.instance().removeEntity(this);
                } else {
                    currentTarget = path.get(pathIndex);
                }
            }
            
            direction = currentTarget.subtract(currentPos);
            
            double movement = speed*deltaTime;
            movement = onMovement(movement);
            
            currentPos = currentPos.add(movement, direction.normalize());
            
            x = currentPos.getX();
            y = currentPos.getY();
        }
    }
    
    public double getAngle() {
        Vector2D normalVector = new Vector2D(1,0);
        
        double dot = direction.dotProduct(normalVector);
        double det = direction.getX() * normalVector.getY() + normalVector.getX() * direction.getY();
        return Math.atan2(det, dot);
    }
}
