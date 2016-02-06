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
    
    private static final double HEALTH_BAR_OFFSET = 1;
    private static final double HEALTH_BAR_HEIGHT = 1;
    private static final double HEALTH_BAR_MARGIN = 0.25;

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
        draw.drawRectangle(x, y-HEALTH_BAR_OFFSET, width, HEALTH_BAR_HEIGHT, Color.black);
        double healthFactor = currentHealth / maxHealth;
        draw.fillRectangle(x + HEALTH_BAR_MARGIN, y-HEALTH_BAR_OFFSET + HEALTH_BAR_MARGIN, healthFactor*(width-2*HEALTH_BAR_MARGIN), HEALTH_BAR_HEIGHT - HEALTH_BAR_MARGIN, Color.green);
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
