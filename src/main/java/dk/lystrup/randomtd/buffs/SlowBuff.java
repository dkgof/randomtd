/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.buffs;

import dk.lystrup.randomtd.domain.Buff;
import dk.lystrup.randomtd.domain.Entity;

/**
 *
 * @author Thor
 */
public class SlowBuff extends Buff {
    
    private final double slowFactor;

    public SlowBuff(Entity caster, Entity target, double duration, String fxImagePath, double fxSize, double slowFactor) {
        super(caster, target, duration, fxImagePath, fxSize, 0, 0);
        this.slowFactor = slowFactor;
    }

    @Override
    public double onMovement(double movement) {
        return movement * slowFactor;
    }

    @Override
    public void onRemoval() {
    }
}
