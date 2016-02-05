/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import java.awt.Graphics2D;

/**
 *
 * @author rolf
 */
public abstract class Entity {
    protected double x;
    
    protected double y;
    
    protected String name;
    
    public abstract void draw(Graphics2D g);
}
