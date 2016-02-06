/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.projectiles.HolyGrenade;

/**
 *
 * @author Thor
 */
public class GraveTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_Grave.png";

    public GraveTower(double x, double y) {
        super(x, y, 5, 5, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return new HolyGrenade(x, y, target, getProjectileSpeed(), getDamage(), getSplashRadius(), getMinSplashFactor(), Projectile.DamageType.EXPLOSIVE);
    }

    @Override
    protected double getDamage() {
        return 10 + 3 * level;
    }

    @Override
    protected double getRange() {
        return 25 + 5 * level;
    }

    @Override
    protected double getProjectileSpeed() {
        return 20;
    }

    @Override
    protected double getFireRate() {
        return Math.max(0.1, 1 - 0.03 * level);
    }

    protected double getSplashRadius() {
        return 10 + 2 * level;
    }

    protected double getMinSplashFactor() {
        return Math.max(0.8, 0.3 + 0.05 * level);
    }

}
