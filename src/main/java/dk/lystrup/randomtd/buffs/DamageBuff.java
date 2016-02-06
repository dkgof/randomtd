/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.buffs;

import dk.lystrup.randomtd.domain.Buff;
import dk.lystrup.randomtd.domain.Entity;
import java.util.function.Function;

/**
 *
 * @author Thor
 */
public class DamageBuff extends Buff {

    private static final double OFFSET_Y = -4;

    private final Function<Void, Double> modifierFunction;

    public DamageBuff(Entity caster, Entity target, String fxImagePath, double fxSize, Function<Void, Double> modifierFunction) {
        super(caster, target, fxImagePath, fxSize, 0, OFFSET_Y);
        this.modifierFunction = modifierFunction;
    }

    @Override
    public void onRemoval() {
    }

    @Override
    public double onDamageDone(double damage) {
        return damage * modifierFunction.apply(null);
    }

}
