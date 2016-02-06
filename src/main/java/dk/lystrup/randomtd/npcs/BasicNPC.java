/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.npcs;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.NPC.ArmorType;
import dk.lystrup.randomtd.ui.GamePanel;

/**
 *
 * @author Thor
 */
public class BasicNPC extends NPC{
    public BasicNPC(String name, String model, double size, double health, double armor, ArmorType armorType, double movementSpeed) {
        super(GamePanel.instance().getStartX(), GamePanel.instance().getStartY(), size, size, "./images/npcs/"+model, movementSpeed);
        
        this.maxHealth = health;
        this.currentHealth = health;
        
        this.armor = armor;
        this.armorType = armorType;
        
        this.flying = false;
        this.name = name;
    }
}
