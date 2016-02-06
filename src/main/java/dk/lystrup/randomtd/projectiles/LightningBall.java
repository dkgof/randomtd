/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.engine.DrawHelper;

/**
 *
 * @author Thor
 */
public class LightningBall extends Projectile{

    
    
    public LightningBall(double x, double y, NPC target, double speed, double damage, DamageType type) {
        super(x, y, target, speed, damage, type, "images/projectiles/Projectile_LightningBall", 0.5, 0.5);
    }
    
    @Override
    protected void onDeath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(DrawHelper draw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
