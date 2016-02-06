/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.projectiles;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.Projectile;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.util.EntityUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thor
 */
public class LightningBall extends Projectile{
    
    private int bouncesLeft;
    private double bounceRange;
    private List<Entity> targetsHit;
    
    public LightningBall(double x, double y, NPC target, double speed, double damage, DamageType type, double bounceRange, int bouncesLeft) {
        this(x, y, target, speed, damage, type, bounceRange, bouncesLeft, new ArrayList<>());        
    }
    
    public LightningBall(double x, double y, NPC target, double speed, double damage, DamageType type, double bounceRange, int bouncesLeft, List<Entity> hits) {
        super(x,y, target, speed, damage, type, "images/projectiles/Projectile_LightningBall.png", 1, 1);
        this.bouncesLeft = bouncesLeft;
        targetsHit = hits;
        targetsHit.add(target);
    }
    
    @Override
    protected void onDeath() {
        if(bouncesLeft <= 0){
            return;
        }
        NPC nextTarget = (NPC) EntityUtil.closestEntityOfType(x, y, bounceRange, NPC.class, targetsHit);
        //TODO next target always null right now
        if(nextTarget != null){
            LightningBall nextBall = new LightningBall(x, y, nextTarget, speed, damage, damageType, bounceRange, bouncesLeft-1, targetsHit);
            GamePanel.instance().addEntity(nextBall);
        }else{
            System.out.println("No next bounce target");
        }
    }
    
}
