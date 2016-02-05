/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.towers;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.domain.Tower;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.projectiles.Arrow;
import dk.lystrup.randomtd.ui.GamePanel;
import java.awt.Color;
import java.util.Collection;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Thor
 */
public class ArrowTower extends Tower{
    
    private static final double ARROW_SPEED = 10;
    private static final int ARROW_DAMAGE = 5;
    private static final int TOWER_WIDTH = 5;
    private static final int TOWER_HEIGHT = 5;
    
    private final double fireCooldown;
    private double fireTimer;

    public ArrowTower(double x, double y) {
        super(x,y);
        
        fireCooldown = 1;
        fireTimer = 0;
        range = 50;
    }
    
    @Override
    public void draw(DrawHelper draw) {
        draw.fillRectangle(x, y, TOWER_WIDTH, TOWER_HEIGHT, Color.red);
    }    

    @Override
    public void tick(double deltaTime) {
        fireTimer += deltaTime;
        if(fireTimer >= fireCooldown){
            if(shootProjectile()){
                fireTimer -= fireCooldown;
            }
        }
    }

    @Override
    protected Projectile generateProjectile(NPC target) {
       return new Arrow(x, y, target, ARROW_SPEED, ARROW_DAMAGE, Projectile.DamageType.PHYSICAL);
    }
    
    
}
