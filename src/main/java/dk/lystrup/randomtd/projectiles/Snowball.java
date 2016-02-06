/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.buffs.SlowBuff;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;

/**
 *
 * @author Thor
 */
public class Snowball extends Projectile {

    private static final String SNOWBALL_PATH = "images/projectiles/Projectile_Snowball.png";
    private static final String SNOW_SLOW_EFFECT_PATH = "images/effects/Effect_SnowSlow.png";

    private final double slowFactor, slowDuration;

    public Snowball(double x, double y, NPC target, Entity owner, double speed, double damage, DamageType type, double slowDuration, double slowFactor) {
        super(x, y, target, owner, speed, damage, type, SNOWBALL_PATH, 2, 2);
        this.slowFactor = slowFactor;
        this.slowDuration = slowDuration;
    }

    @Override
    protected void onCollision(Entity t, boolean isPrimaryTarget) {
        t.addBuff(new SlowBuff(owner, t, slowDuration, SNOW_SLOW_EFFECT_PATH, 2, slowFactor));
    }
}
