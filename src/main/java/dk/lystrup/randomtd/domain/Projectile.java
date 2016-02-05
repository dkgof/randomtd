/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.domain.NPC.ArmorType;
import dk.lystrup.randomtd.ui.GamePanel;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author rolf
 */
public abstract class Projectile extends Entity {

    private static final double COLLISION_RADIUS = 10;

    public enum DamageType {

        //(none, light, medium, heavy)
        PHYSICAL(generateDamageMap(1.1, 1.0, 0.9, 0.8)),
        MAGICAL(generateDamageMap(1, 0.8, 0.9, 1.1));

        DamageType(Map<ArmorType, Double> dmgMap) {
            damageMultipliers = dmgMap;
        }

        private final Map<ArmorType, Double> damageMultipliers;

        private static Map<ArmorType, Double> generateDamageMap(double... multipliers) {
            Map<ArmorType, Double> dmgMap = new HashMap<>();

            int i = 0;
            for (ArmorType armorType : ArmorType.values()) {
                dmgMap.put(armorType, multipliers[i]);
                i++;
            }

            return dmgMap;
        }

        public double getDamageVersus(ArmorType armor) {
            return damageMultipliers.get(armor);
        }
    }

    protected NPC target;
    protected double speed;
    protected double damage;
    protected DamageType damageType;

    public Projectile(double x, double y, NPC target, double speed, double damage, DamageType type) {
        super(x, y);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.damageType = type;
    }

    @Override
    public void tick(double deltaTime) {
        Vector2D targetVector = new Vector2D(target.getX(), target.getY());
        Vector2D myVector = new Vector2D(x, y);

        Vector2D dirVector = targetVector.subtract(myVector);
        //check for collision
        if (dirVector.getNorm() < COLLISION_RADIUS) {
            //collision happened, do something
            target.doDamage(this);
            onDeath();
            GamePanel.instance().removeEntity(this);
        } else {
            //otherwise move towards target
            Vector2D norm = dirVector.normalize();
            x += speed * norm.getX() * deltaTime;
            y += speed * norm.getY() * deltaTime;
        }
    }

    public double getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }
    
    
    
    protected abstract void onDeath();
}
