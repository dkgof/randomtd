/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Projectile;
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
    public void draw(Graphics2D g) {
        
    }

    @Override
    public void tick(double deltaTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
