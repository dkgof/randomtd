/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.util.BuffUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rolf
 */
public abstract class Entity {

    protected final List<Buff> buffs, addBuffs, removeBuffs;
    protected double x;
    protected double y;
    protected String name;
    protected boolean isActive;

    public abstract void draw(DrawHelper draw);

    public void tick(double deltaTime) {
        synchronized (buffs) {
            buffs.addAll(addBuffs);
            addBuffs.clear();
            buffs.removeAll(removeBuffs);
            removeBuffs.clear();

            buffs.stream().forEach((b) -> {
                b.onTick(deltaTime);
            });
        }
    }

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
        buffs = new ArrayList<>();
        addBuffs = new ArrayList<>();
        removeBuffs = new ArrayList<>();
        isActive = true;
    }

    public void addBuff(Buff b) {
        addBuffs.add(b);
    }

    public void removeBuff(Buff b) {
        removeBuffs.add(b);
    }

    public void destroyBuffs() {
        synchronized (buffs) {
            addBuffs.clear();
            removeBuffs.addAll(buffs);
            buffs.stream().forEach((buff) -> {
                buff.destroy();
            });
        }
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    protected double onDamageDone(double amount) {
        synchronized (buffs) {
            for (Buff b : buffs) {
                amount = b.onDamageDone(amount);
            }
        }
        return amount;
    }

    protected double onDamageTaken(double amount) {
        synchronized (buffs) {
            for (Buff b : buffs) {
                amount = b.onDamageTaken(amount);
            }
        }
        return amount;
    }

    protected double onMovement(double speed) {
        synchronized (buffs) {
            for (Buff b : buffs) {
                speed = b.onMovement(speed);
            }
        }
        return speed;
    }

    public boolean hasBuffOfType(Class c) {
        synchronized (buffs) {
            return buffs.stream().anyMatch((buff) -> buff.getId().equals(BuffUtil.getBuffId(c)));
        }
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public void setIsActive(boolean value){
        isActive = value;
    }
}
