/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd;

import dk.lystrup.randomtd.npcs.RandomWalkNPC;
import dk.lystrup.randomtd.towers.ArrowTower;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.ui.MainWindow;

/**
 *
 * @author rolf
 */
public class Main {
    public static void main(String[] args) {
        new MainWindow();
        
        GamePanel.instance().addEntity(new ArrowTower(20, 20));
        
        GamePanel.instance().setLevelImages("./images/maps/#1 Background.png", "./images/maps/#1 NpcPath.png", "./images/maps/#1 TowerMask.png");
        
        for(int i = 0; i<50; i++) {
            GamePanel.instance().addEntity(new RandomWalkNPC());
        }
    }
}
