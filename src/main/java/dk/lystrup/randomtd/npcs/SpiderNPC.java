/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.npcs;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.ui.GamePanel;
import java.util.Random;

/**
 *
 * @author rolf
 */
public class SpiderNPC extends NPC {

    public SpiderNPC() {
        super(GamePanel.instance().getStartX(), GamePanel.instance().getStartY(), 2, 2, "./images/npcs/NPC_Spider.png", 10.0);
        
        this.maxHealth = 100;
        this.currentHealth = 100;
        
        this.armor = 0;
        this.armorType = ArmorType.LIGHT;
        
        this.flying = false;
        this.name = "Creepy Spider";
    }
}
