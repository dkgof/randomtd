/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

/**
 *
 * @author rolf
 */
public abstract class Tower extends Entity {
    
    protected double price;
    
    public Tower(double x, double y) {
        super(x,y);
    }
}
