/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.ArrowTower;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Thor
 */
public class Arrow extends Projectile{

    private BufferedImage img;

    public Arrow(double x, double y, NPC target, double speed, int damage, DamageType type) {
        super(x, y, target, speed, damage, type);
    }

    @Override
    protected void onDeath() {
        
    }
    
    @Override
    public void draw(DrawHelper draw) {
        if(img == null) {
            try {
                img = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/projectiles/Projectile_Arrow.png"));
            } catch (IOException ex) {
                Logger.getLogger(ArrowTower.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        draw.drawImage(x, y, 0.5, 0.5, img);
    }
    
}
