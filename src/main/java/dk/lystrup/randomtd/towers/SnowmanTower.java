/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.projectiles.Snowball;

/**
 *
 * @author Thor
 */
public class SnowmanTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_Snowman.png";

    public SnowmanTower(double x, double y) {
        super(x, y, 5, 5, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return new Snowball(x, y, target, this, getProjectileSpeed(), getDamage(), getSplashRadius(), getMinSplashFactor(), Projectile.DamageType.COLD, getSlowDuration(), getSlowFactor());
    }

    @Override
    protected double getDamage() {
        return 5 + level;
    }

    @Override
    protected double getRange() {
        return 25 + 2 * level;
    }

    @Override
    protected double getProjectileSpeed() {
        return 25;
    }

    @Override
    protected double getFireRate() {
        return Math.max(0.1, 1 - 0.06 * level);
    }
    
    protected double getSlowDuration(){
        return 2;
    }
    
    protected double getSlowFactor(){
        return 0.5;
    }

    @Override
    protected double getSplashRadius() {
        if(level >= 5){
            return 5 + level;
        }
        return 0;
    }

    @Override
    protected double getMinSplashFactor() {
        return 0.2;
    }
    
    

}
