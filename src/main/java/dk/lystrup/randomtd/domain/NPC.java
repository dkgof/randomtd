/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

/**
 *
 * @author rolf
 */
public abstract class NPC extends Entity {
    
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
    
    public abstract void doDamage(Projectile p);
}
