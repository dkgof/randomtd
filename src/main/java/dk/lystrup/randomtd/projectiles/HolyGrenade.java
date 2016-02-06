/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.util.EntityUtil;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Thor
 */
public class HolyGrenade extends Projectile {

    private final double explosionRadius;
    
    public HolyGrenade(double x, double y, NPC target, double speed, double damage, DamageType type, double explosionRadius) {
        super(x, y, target, speed, damage, type, "images/projectiles/Projectile_HolyGrenade.png", 2, 2);
        this.explosionRadius = explosionRadius;
    }

    @Override
    protected void onDeath() {
        List<Entity> explosionTargets =  EntityUtil.entitiesInRangeOfType(x, y, explosionRadius, NPC.class, target);
        NPC npc;
        for (Entity e : explosionTargets) {
            npc = (NPC) e;
            //TODO reduce damage based on distance to center of explosion
            npc.doDamage(this, damage);
        }
    }
}
