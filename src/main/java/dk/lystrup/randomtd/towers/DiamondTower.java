/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.buffs.DamageBuff;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.util.EntityUtil;
import dk.lystrup.randomtd.util.Pair;
import java.util.List;

/**
 *
 * @author Thor
 */
public class DiamondTower extends Tower {

    private static final String IMAGE_PATH = "images/towers/Tower_Diamond.png";
    private static final String SHARPEN_EFFECT_PATH = "images/effects/Effect_DiamondBuff.png";
    private static final double SHARPEN_EFFECT_SIZE = 5;

    public DiamondTower(double x, double y) {
        super(x, y, 5, 7, IMAGE_PATH);
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
        return null;
    }

    @Override
    protected double getDamage() {
        return 0;
    }

    @Override
    protected double getRange() {
        return 30 + 3 * level;
    }

    @Override
    protected double getFireRate() {
        return 0;
    }

    @Override
    protected double getProjectileSpeed() {
        return 0;
    }

    private Double getDamageModifier(Void v) {
        return 1.5 + 0.08 * level;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        List<Pair<Entity, Double>> buffTargets = EntityUtil.entitiesInRangeOfType(x, y, getRange(), Tower.class, this);
        buffTargets.stream().forEach((buffTarget) -> {
            if (!buffTarget.first.hasBuffOfType(DamageBuff.class)) {
                buffTarget.first.addBuff(new DamageBuff(this, buffTarget.first, SHARPEN_EFFECT_PATH, SHARPEN_EFFECT_SIZE, this::getDamageModifier));
            }
        });

    }

}
