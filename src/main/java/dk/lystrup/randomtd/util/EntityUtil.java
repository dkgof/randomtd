/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.util;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.ui.GamePanel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author Thor
 */
public class EntityUtil {
    public static Entity closestEntityOfType(double x, double y, double maxDistance, Class parentClass, Collection<Entity> exclude){
        Collection<Entity> potentialEntities = GamePanel.instance().getEntities();
        if (potentialEntities.isEmpty()) {
            return null;
        }
        //just some large number...
        double closestDistance = Double.MAX_VALUE;
        double dist;
        Entity closest = null;
        Vector2D fromPoint = new Vector2D(x, y);
        for (Entity e : potentialEntities) {
            if (!(parentClass.isInstance(e)) || (exclude != null && exclude.contains(e))) {
                continue;
            }
            dist = Vector2D.distance(fromPoint, new Vector2D(e.getX(), e.getY()));
            if (dist < closestDistance) {
                closestDistance = dist;
                closest = e;
            }
        }
        if(closestDistance <= maxDistance){
            return closest;
        }else{
            return null;
        }
    }
    
    public static List<Entity> entitiesInRangeOfType(double x, double y, double maxDistance, Class parentClass, Entity exclude){
        Collection<Entity> potentialEntities = GamePanel.instance().getEntities();
        if (potentialEntities.isEmpty()) {
            return null;
        }
        //just some large number...
        double dist;
        List<Entity> res = new ArrayList<>();
        Vector2D fromPoint = new Vector2D(x, y);
        for (Entity e : potentialEntities) {
            if (!(parentClass.isInstance(e)) || (e == exclude)) {
                continue;
            }
            dist = Vector2D.distance(fromPoint, new Vector2D(e.getX(), e.getY()));
            if (dist < maxDistance) {
                res.add(e);
            }
        }
        return res;
    }
}
