/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;

/**
 *
 * @author Thor
 */
public class Arrow extends Projectile {

    public Arrow(double x, double y, NPC target, double speed, double damage, DamageType type) {
        super(x, y, target, speed, damage, type, "images/projectiles/Projectile_Arrow.png", 2, 0.5);
    }

    @Override
    protected void onDeath() {

    }
}
