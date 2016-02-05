/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd;

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
        
        GamePanel.instance().addEntity(new ArrowTower(200, 200));
    }
}
