/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.util.EntityUtil;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Thor
 */
public class LightningBall extends Projectile{
    
    private double bounceChance;
    private double bounceRange;
    private Queue<Entity> targetsHit;
    
    public LightningBall(double x, double y, NPC target, Entity owner, double speed, double damage, DamageType type, double bounceRange, double bounceChance) {
        this(x, y, target, owner, speed, damage, type, bounceRange, bounceChance, new LinkedList<>());        
    }
    
    public LightningBall(double x, double y, NPC target, Entity owner, double speed, double damage, DamageType type, double bounceRange, double bounceChance, Queue<Entity> hits) {
        super(x,y, target, owner, speed, damage, type, "images/projectiles/Projectile_LightningBall.png", 1.5, 1.5);
        this.bounceChance = bounceChance;
        this.bounceRange = bounceRange;
        targetsHit = hits;
        targetsHit.add(target);
        if(targetsHit.size() >= 3){
            targetsHit.poll();
        }
    }
    
    @Override
    protected void onCollision() {
        if(Math.random() > bounceChance){
            return;
        }
        NPC nextTarget = (NPC) EntityUtil.closestEntityOfType(x, y, bounceRange, NPC.class, targetsHit);
        //TODO next target always null right now
        if(nextTarget != null){
            LightningBall nextBall = new LightningBall(x, y, nextTarget, owner, speed, damage, damageType, bounceRange, bounceChance, targetsHit);
            GamePanel.instance().addEntity(nextBall);
        }
    }
    
}
