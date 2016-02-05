/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.domain.NPC.ArmorType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rolf
 */
public abstract class Projectile extends Entity {

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
            for(ArmorType armorType : ArmorType.values()){
                dmgMap.put(armorType, multipliers[i]);
                i++;    
            }
            
            return dmgMap;
        }

        public double getDamageVersus(ArmorType armor) {
            return damageMultipliers.get(armor);
        }
    }
    
    protected Entity target;
    protected double speed;
    
    public Projectile(double x, double y) {
        super(x, y);
    }

    @Override
    public void tick(double deltaTime) {
    }
    
    
}
