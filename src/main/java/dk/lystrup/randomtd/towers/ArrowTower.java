/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.engine.DrawHelper;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Thor
 */
public class ArrowTower extends Tower{
    
    private static final int TOWER_WIDTH = 20;
    private static final int TOWER_HEIGHT = 25;

    public ArrowTower(double x, double y) {
        super(x,y);
        
        price = 5;
    }
    
    @Override
    public void draw(DrawHelper draw) {
        draw.fillRectangle(x, y, TOWER_WIDTH, TOWER_HEIGHT, Color.red);
    }    

    @Override
    public void tick(double deltaTime) {
    }
    
}
