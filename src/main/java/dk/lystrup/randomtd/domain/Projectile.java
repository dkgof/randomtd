/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.domain.NPC.ArmorType;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.ArrowTower;
import dk.lystrup.randomtd.ui.GamePanel;
import dk.lystrup.randomtd.util.EntityUtil;
import dk.lystrup.randomtd.util.Pair;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author rolf
 */
public abstract class Projectile extends Entity {

    private static final double COLLISION_RADIUS = 0.5;

    public enum DamageType {

        //(none, light, medium, heavy)
        PHYSICAL(generateDamageMap(1.1, 1.0, 0.9, 0.8)),
        MAGICAL(generateDamageMap(1, 0.8, 0.9, 1.1)),
        ELECTRICAL(generateDamageMap(1, 1, 1, 1)),
        EXPLOSIVE(generateDamageMap(1, 1, 1, 1)),
        FIRE(generateDamageMap(1, 1, 1, 1)),
        COLD(generateDamageMap(1, 1, 1, 1));

        DamageType(Map<ArmorType, Double> dmgMap) {
            damageMultipliers = dmgMap;
        }

        private final Map<ArmorType, Double> damageMultipliers;

        private static Map<ArmorType, Double> generateDamageMap(double... multipliers) {
            Map<ArmorType, Double> dmgMap = new HashMap<>();

            int i = 0;
            for (ArmorType armorType : ArmorType.values()) {
                dmgMap.put(armorType, multipliers[i]);
                i++;
            }

            return dmgMap;
        }

        public double getDamageVersus(ArmorType armor) {
            return damageMultipliers.get(armor);
        }
    }

    private BufferedImage img;
    private final String imagePath;
    private final double width, height;

    protected NPC target;
    protected Entity owner;
    protected double speed;
    protected double damage;
    protected double splashRadius;
    protected double minSplashFactor;
    protected DamageType damageType;

    public Projectile(double x, double y, NPC target, Entity owner, double speed, double damage, DamageType type, String imagePath, double width, double height) {
        this(x, y, target, owner, speed, damage, 0, 0, type, imagePath, width, height);
    }

    public Projectile(double x, double y, NPC target, Entity owner, double speed, double damage, double splashRadius, double minSplashFactor, DamageType type, String imagePath, double width, double height) {
        super(x, y);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.damageType = type;
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.splashRadius = splashRadius;
        this.minSplashFactor = minSplashFactor;
        this.owner = owner;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        Vector2D targetVector = new Vector2D(target.getX(), target.getY());
        Vector2D myVector = new Vector2D(x, y);

        Vector2D dirVector = targetVector.subtract(myVector);
        //check for collision
        if (dirVector.getNorm() < COLLISION_RADIUS) {
            //collision happened, do something
            //doDamage is responsible for factoring in buffs.
            target.doDamage(owner, damageType, damage);
            onCollision(target, true);
            
            if (splashRadius > 0) {
                List<Pair<Entity, Double>> explosionTargets = EntityUtil.entitiesInRangeOfType(x, y, splashRadius, NPC.class, target);
                NPC npc;
                double splashFactor, relativeDist;
                for (Pair<Entity, Double> t : explosionTargets) {
                    npc = (NPC) t.first;
                    relativeDist = t.second / splashRadius;
                    splashFactor = 1 - relativeDist * (1 - minSplashFactor);
                    npc.doDamage(owner, damageType, damage * splashFactor);
                    onCollision(npc, false);
                }
            }
            
            GamePanel.instance().removeEntity(this);
        } else {
            //otherwise move towards target
            Vector2D norm = dirVector.normalize();
            x += speed * norm.getX() * deltaTime;
            y += speed * norm.getY() * deltaTime;
        }
    }

    @Override
    public void draw(DrawHelper draw) {
        if (img == null) {
            try {
                img = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imagePath));
            } catch (IOException ex) {
                Logger.getLogger(ArrowTower.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        draw.drawImage(x, y, width, height, img, getAngle());
    }

    public double getDamage() {
        return damage;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public double getAngle() {
        Vector2D targetVector = new Vector2D(target.getX(), target.getY());
        Vector2D myVector = new Vector2D(x, y);

        Vector2D dirVector = targetVector.subtract(myVector);
        Vector2D normalVector = new Vector2D(1, 0);

        double dot = dirVector.dotProduct(normalVector);
        double det = dirVector.getX() * normalVector.getY() + normalVector.getX() * dirVector.getY();
        return Math.atan2(det, dot);
    }

    protected void onCollision(Entity target, boolean isPrimaryTarget){
    }
}
