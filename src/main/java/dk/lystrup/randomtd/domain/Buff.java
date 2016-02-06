/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.util.BuffUtil;

/**
 *
 * @author Thor
 */
public abstract class Buff {
    protected Entity target, caster;
    private String id;
    protected Effect fx;
    protected double duration;
    protected boolean indefiniteDuration;

    public Buff(Entity caster, Entity target, double duration, String fxImagePath, double fxSize, double offsetX, double offsetY) {
        if (!fxImagePath.equals("")) {
            if(duration == -1){
                fx = new Effect(target, fxSize, fxImagePath, offsetX, offsetY);
            }else{
                fx = new Effect(target, fxSize, duration, fxImagePath, offsetX, offsetY);
            }
            GamePanel.instance().addEntity(fx);
        }
        this.caster = caster;
        this.target = target;
        this.duration = duration;
        indefiniteDuration = false;
        this.id = BuffUtil.getBuffId(this.getClass());
    }
    
    public Buff(Entity caster, Entity target, String fxImagePath, double fxSize, double offsetX, double offsetY) {
        this(caster, target, -1, fxImagePath, fxSize, offsetX, offsetY);
        indefiniteDuration = true;
    }
    
    public Buff(Entity caster, Entity target, double duration) {
        this(caster, target, duration, "", 0, 0, 0);
    }
    
    public Buff(Entity caster, Entity target) {
        this(caster, target, -1, "", 0, 0, 0);
        indefiniteDuration = true;
    }
    
    /**
     * Callback for whenever damage is dealt by the buffed entity
     * @param damage amount of damage to be dealt.
     * @return damage done after this buff's modifications
     */
    public double onDamageDone(double damage){
        return damage;
    }
    
    /**
     * Callback for whenever damage is taken by the buffed entity
     * @param damage amount of damage to be taken.
     * @return damage taken after this buff's modifications.
     */
    public double onDamageTaken(double damage){
        return damage;
    }
    
    /**
     * Callback for whenever the buffed entity moves
     * @param movement distance moved by the entity
     * @return distance to move after this buff's modifications.
     */
    public double onMovement(double movement){
        return movement;
    }
    
    /**
     * Callback for whenever a time tick happens for the buffed entity
     * @param deltaTime for the time tick.
     */
    public void onTick(double deltaTime){
        if(indefiniteDuration){
            return;
        }
        duration -= deltaTime;
        if(duration <= 0){
            target.removeBuff(this);
            onRemoval();
        }
    }

    public abstract void onRemoval();
    
    public void destroy(){
        duration = 0;
        indefiniteDuration = false;
        fx.destroy();
    }

    /**
     * @return the id unique to this type of buff (one per class)
     */
    public String getId() {
        return id;
    }
    
}
