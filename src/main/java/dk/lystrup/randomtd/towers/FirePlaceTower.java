/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.projectiles.FireBall;

/**
 *
 * @author Thor
 */
public class FirePlaceTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_FirePlace.png";

    public FirePlaceTower(double x, double y) {
        super(x, y, 5, 5, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return new FireBall(x, y, target, this, getProjectileSpeed(), getDamage(), Projectile.DamageType.FIRE, getFireDamage(), getFireDuration());
    }

    @Override
    protected double getDamage() {
        return 4 + level;
    }

    @Override
    protected double getRange() {
        return 30 + 3 * level;
    }

    @Override
    protected double getProjectileSpeed() {
        return 25;
    }

    @Override
    protected double getFireRate() {
        return Math.max(0.1, 2 - 0.05 * level);
    }
    
    protected double getFireDamage(){
        return 60 + 5 * level;
    }
    
    protected double getFireDuration(){
        return 3;
    }

}
