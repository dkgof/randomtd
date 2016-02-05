/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.npcs;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.engine.DrawHelper;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author rolf
 */
public class RandomWalkNPC extends NPC {

    private static final Random rand = new Random();
    
    public RandomWalkNPC() {
        super(rand.nextInt(100), rand.nextInt(100));
    }
    
    @Override
    public void doDamage(Projectile p) {
    }

    @Override
    public void draw(DrawHelper draw) {
        draw.drawRectangle(x, y, 2, 2, Color.green);
    }

    @Override
    public void tick(double deltaTime) {
        if(rand.nextInt(100) < 50) {
            x += deltaTime * 2;
        } else {
            x -= deltaTime * 2;
        }
        
        if(rand.nextInt(100) < 50) {
            y += deltaTime * 2;
        } else {
            y -= deltaTime * 2;
        }
        
        x = Math.min(Math.max(x, 0), 100);
        y = Math.min(Math.max(y, 0), 100);
    }
}
