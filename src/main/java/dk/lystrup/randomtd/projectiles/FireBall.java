/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.buffs.DamageOverTimeBuff;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;

/**
 *
 * @author Thor
 */
public class FireBall extends Projectile {

    private static final String FIRE_IMAGE_PATH = "images/effects/Effect_Fire.png";
    
    private final double fireDuration,fireDamage;
    
    public FireBall(double x, double y, NPC target, Entity owner, double speed, double damage, DamageType type, double fireDamage, double fireDuration) {
        super(x, y, target, owner, speed, damage, type, "images/projectiles/Projectile_FireBall.png", 2, 2);
        this.fireDamage = fireDamage;
        this.fireDuration = fireDuration;
    }

    @Override
    protected void onCollision() {
        target.addBuff(new DamageOverTimeBuff(owner, target, fireDuration, FIRE_IMAGE_PATH, 2, fireDamage, damageType));
    }
}
