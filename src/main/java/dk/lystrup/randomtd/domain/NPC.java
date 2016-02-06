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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author rolf
 */
public abstract class NPC extends Entity {

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

    public NPC(double x, double y, double width, double height, String imagePath) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }

    public void doDamage(Projectile p, double amount) {
        double dmg = (amount - armor) * p.getDamageType().getDamageVersus(armorType);
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
        draw.drawImage(x, y, width, height, img, 0);
    }
    
    @Override
    public void tick(double deltaTime) {
    }
}
