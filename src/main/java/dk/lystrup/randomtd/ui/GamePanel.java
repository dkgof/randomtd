/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.ui;

import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.engine.Graphics2DDrawHelper;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author rolf
 */
public class GamePanel extends JPanel {

    private static GamePanel instance;
    
    public static GamePanel instance() {
        if(instance == null) {
            instance = new GamePanel();
        }
        
        return instance;
    }
    
    private final List<Entity> entities;
    
    private GamePanel() {
        entities = new ArrayList<>();
    }
    
    private long lastTime;
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        
        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.white);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());
        
        synchronized(entities) {
            
            double deltaTime = (System.nanoTime() - lastTime) / 1000000000.0;
            
            entities.parallelStream().forEach((entity) -> {
                entity.tick(deltaTime);
            });

            DrawHelper draw = new Graphics2DDrawHelper(g2, this.getWidth(), this.getHeight());
            
            entities.parallelStream().forEach((entity) -> {
                entity.draw(draw);
            });
            
            lastTime = System.nanoTime();
        }
    }    

    public void addEntity(Entity e) {
        synchronized(entities) {
            entities.add(e);
        }
    }
    
    public void removeEntity(Entity e) {
        synchronized(entities) {
            entities.remove(e);
        }
    }
}
