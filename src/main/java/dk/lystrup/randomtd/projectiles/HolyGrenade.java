/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Effect;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.ui.GamePanel;

/**
 *
 * @author Thor
 */
public class HolyGrenade extends Projectile {

    private static final String EXPLOSION_FX_PATH = "images/effects/Effect_Explosion.png";

    public HolyGrenade(double x, double y, NPC target, Entity owner, double speed, double damage, double splashRadius, double minSplashDamage, DamageType type) {
        super(x, y, target, owner, speed, damage, splashRadius, minSplashDamage, type, "images/projectiles/Projectile_HolyGrenade.png", 2, 2);
    }

    @Override
    protected void onCollision(Entity t, boolean isPrimaryTarget) {
        if (isPrimaryTarget) {
            Effect fx = new Effect(t.getX(), t.getY(), 0.1, 2 * splashRadius, 0.1, EXPLOSION_FX_PATH);
            GamePanel.instance().addEntity(fx);
        }
    }
}
