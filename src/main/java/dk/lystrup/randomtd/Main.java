/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd;

import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.npcs.SpiderNPC;
import dk.lystrup.randomtd.towers.ArrowTower;
import dk.lystrup.randomtd.towers.GraveTower;
import dk.lystrup.randomtd.towers.TeslaTower;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.ui.MainWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rolf
 */
public class Main {
    public static void main(String[] args) {
        new MainWindow();
        
        GamePanel.instance().setLevelImages("./images/maps/#1 Background.png", "./images/maps/#1 NpcPath.png", "./images/maps/#1 TowerMask.png");
        
        Tower tower = new GraveTower(20, 20);
        //GamePanel.instance().addEntity(tower);

        for(int i = 0; i<100; i++) {
            GamePanel.instance().addEntity(new SpiderNPC());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
