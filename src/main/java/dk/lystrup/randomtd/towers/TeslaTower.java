/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.projectiles.LightningBall;

/**
 *
 * @author Thor
 */
public class TeslaTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_Tesla.png";
    
    public TeslaTower(double x, double y) {
        super(x, y, 5, 5, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return new LightningBall(x, y, target, getProjectileSpeed(), getDamage(), Projectile.DamageType.ELECTRICAL);
    }

    @Override
    protected double getDamage() {
        return 9 + 3 * level;
    }

    @Override
    protected double getRange() {
        return 30 + 4 * level;
    }

    @Override
    protected double getProjectileSpeed() {
        return 25;
    }

    @Override
    protected double getFireRate() {
        return 1;
    }

}
