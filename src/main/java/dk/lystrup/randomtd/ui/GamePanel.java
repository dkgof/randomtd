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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author rolf
 */
public class GamePanel extends JPanel {

    private static GamePanel instance;

    public static GamePanel instance() {
        if (instance == null) {
            instance = new GamePanel();
        }

        return instance;
    }

    private final List<Entity> entities;
    private final List<Entity> addEntities;
    private final List<Entity> removeEntities;

    private GamePanel() {
        entities = new ArrayList<>();
        addEntities = new ArrayList<>();
        removeEntities = new ArrayList<>();
    }

    private long lastTime = -1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.white);
        g2.clearRect(0, 0, this.getWidth(), this.getHeight());

        if (lastTime == -1) {
            lastTime = System.nanoTime();
        }

        double deltaTime = (System.nanoTime() - lastTime) / 1000000000.0;

        entities.addAll(addEntities);
        addEntities.clear();
        
        entities.removeAll(removeEntities);
        removeEntities.clear();
        
        entities.parallelStream().forEach((entity) -> {
            entity.tick(deltaTime);
        });

        DrawHelper draw = new Graphics2DDrawHelper(g2, this.getWidth(), this.getHeight());

        entities.stream().forEach((entity) -> {
            entity.draw(draw);
        });

        lastTime = System.nanoTime();
    }

    public void addEntity(Entity e) {
        addEntities.add(e);
    }

    public void removeEntity(Entity e) {
        removeEntities.add(e);
    }

    public Collection<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
}
