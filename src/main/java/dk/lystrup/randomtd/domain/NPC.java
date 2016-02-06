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

    protected double maxHealth;

    protected double currentHealth;

    protected double armor;

    protected ArmorType armorType;

    protected double movementSpeed;

    protected boolean flying;

    public NPC(double x, double y) {
        super(x, y);
    }

    public void doDamage(Projectile p, double amount) {
        double dmg = (amount - armor) * p.getDamageType().getDamageVersus(armorType);
        if (dmg > 0) {
            currentHealth -= dmg;
            if(currentHealth <= 0){
                GamePanel.instance().removeEntity(this);
            }
        }
    }
}
