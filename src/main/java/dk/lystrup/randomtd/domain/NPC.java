/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.ui.GamePanel;

/**
 *
 * @author rolf
 */
public abstract class NPC extends Entity {

    /**
     * Declare new armor types last.
     */
    public enum ArmorType {

        NONE,
        LIGHT,
        MEDIUM,
        HEAVY
    }

    private double maxHealth;

    private double currentHealth;

    private double armor;

    private ArmorType armorType;

    private double movementSpeed;

    private boolean flying;

    public NPC(double x, double y) {
        super(x, y);
    }

    public void doDamage(Projectile p) {
        double dmg = (p.getDamage() - armor) * p.getDamageType().getDamageVersus(armorType);
        if (dmg > 0) {
            currentHealth -= dmg;
            if(currentHealth <= 0){
                GamePanel.instance().removeEntity(this);
            }
        }
    }
}
