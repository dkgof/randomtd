/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.ui;

import com.sun.glass.ui.Pixels;
import dk.lystrup.randomtd.domain.Entity;
import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.engine.Graphics2DDrawHelper;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

    private Image background;
    private Image pathMask;
    private Image towerMask;
    
    private GamePanel() {
        entities = new ArrayList<>();
        addEntities = new ArrayList<>();
        removeEntities = new ArrayList<>();
    }

    public void setLevelImages(String background, String path, String towers) {
        try {
            this.background = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(background));
            this.pathMask = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
            this.towerMask = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(towers));
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private long lastTime = -1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.pink);
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

        draw.drawBackground(background);
        
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

    @Override
    public Dimension getPreferredSize() {
        Dimension d = this.getParent().getSize();
        
        double largestSize = Math.min(d.getWidth(), d.getHeight());
        
        return new Dimension((int) largestSize, (int) largestSize);
    }
}
