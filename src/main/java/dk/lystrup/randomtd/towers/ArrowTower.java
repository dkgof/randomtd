/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.projectiles.Arrow;

/**
 *
 * @author Thor
 */
public class ArrowTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_BasicArrow.png";

    public ArrowTower(double x, double y) {
        super(x, y, 5, 5, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return new Arrow(x, y, target, this, getProjectileSpeed(), getDamage(), Projectile.DamageType.PHYSICAL);
    }

    @Override
    protected double getDamage() {
        return 7 + 2 * level;
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
        return Math.max(0.1, 0.8 - 0.05 * level);
    }

}
