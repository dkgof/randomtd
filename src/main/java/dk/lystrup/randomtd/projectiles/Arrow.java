/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.engine.DrawHelper;
import java.awt.Graphics2D;

/**
 *
 * @author Thor
 */
public class Arrow extends Projectile{

    public Arrow(double x, double y) {
        super(x,y);
    }
    
    @Override
    public void draw(DrawHelper draw) {
        //PIL
    }

    @Override
    public void tick(double deltaTime) {
    }
    
}
