/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd;

import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.npcs.SpiderNPC;
import dk.lystrup.randomtd.towers.DiamondTower;
import dk.lystrup.randomtd.towers.GraveTower;
import dk.lystrup.randomtd.towers.SnowmanTower;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.ui.MainWindow;
import dk.lystrup.randomtd.waves.Wave;
import dk.lystrup.randomtd.waves.WaveLoader;
import java.util.List;
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
        GamePanel.instance().loadWaves("./xml/waves.xml");

        Tower tower = new GraveTower(25, 30);
        GamePanel.instance().addEntity(tower);
        tower = new DiamondTower(30, 30);
        GamePanel.instance().addEntity(tower);
        tower = new SnowmanTower(30, 40);
        GamePanel.instance().addEntity(tower);
    }
}
