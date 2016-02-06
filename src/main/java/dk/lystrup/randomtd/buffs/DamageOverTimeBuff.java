/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.buffs;

import dk.lystrup.randomtd.domain.Buff;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile.DamageType;

/**
 *
 * @author Thor
 */
public class DamageOverTimeBuff extends Buff {

    private DamageType type;
    private double damage;

    public DamageOverTimeBuff(Entity caster, Entity target, double duration, String fxImagePath, double fxSize, double damage, DamageType type) {
        super(caster, target, duration, fxImagePath, fxSize, 0, 0);
        if (!NPC.class.isInstance(target)) {
            throw new IllegalArgumentException("Only NPCs can be damaged over time.");
        }
        this.damage = damage;
        this.type = type;
    }

    @Override
    public void onTick(double deltaTime) {
        super.onTick(deltaTime);
        ((NPC) target).doDamage(caster, type, damage * deltaTime);
    }

    @Override
    public void onRemoval() {
    } 
}
