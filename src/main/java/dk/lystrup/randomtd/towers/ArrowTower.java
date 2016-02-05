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
    
    private static final int TOWER_WIDTH = 20;
    private static final int TOWER_HEIGHT = 25;
    private static final double ARROW_SPEED = 10;
    private static final int ARROW_DAMAGE = 5;
    
    private final double fireCooldown;
    private double fireTimer;

    public ArrowTower(double x, double y) {
        super(x,y);
        
        price = 5;
        fireCooldown = 1;
        fireTimer = 0;
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
    
    /**
     * Attempt to fire a projectile.
     * @return true if a projectile was fired, false otherwise
     */
    private boolean shootProjectile(){
        //TODO need a way to find and target NPCs.
        Collection<Entity> targets = GamePanel.instance().getEntities();
        if(targets.isEmpty()){
            return false;
        }
        //just some large number...
        double closestDistance = 99999;
        double dist;
        Entity closest = null;
        Vector2D selfVector = new Vector2D(x, y);
        for(Entity e : targets){
            if(!(e instanceof NPC)){
                continue;
            }
            dist = Vector2D.distance(selfVector, new Vector2D(e.getX(), e.getY()));
            if(dist < closestDistance){
                closestDistance = dist;
                closest = e;
            }
        }
        if(closest != null){
            Arrow arrow = new Arrow(x, y, (NPC) closest, ARROW_SPEED, ARROW_DAMAGE, Projectile.DamageType.PHYSICAL);
            GamePanel.instance().addEntity(arrow);
            return true;
        }
        return false;
    }
    
    
}
