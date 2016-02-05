/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.projectiles.Arrow;
import dk.lystrup.randomtd.ui.GamePanel;
import java.util.Collection;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author rolf
 */
public abstract class Tower extends Entity {
    
    protected double price;
    protected double range;
    
    public Tower(double x, double y) {
        super(x,y);
    }
    
    /**
     * Attempt to fire a projectile.
     * @return true if a projectile was fired, false otherwise
     */
    protected boolean shootProjectile(){
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
            if(closestDistance > range){
                return false;
            }
            Projectile p = generateProjectile((NPC)closest);
            GamePanel.instance().addEntity(p);
            return true;
        }
        return false;
    }
    
    protected abstract Projectile generateProjectile(NPC target);
}
