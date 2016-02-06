/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.domain;

import dk.lystrup.randomtd.engine.DrawHelper;
import dk.lystrup.randomtd.towers.TeslaTower;
import dk.lystrup.randomtd.ui.GamePanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Thor
 */
public class Effect extends Entity{

    private BufferedImage img;
    private final String imagePath;
    private final double widthDiff, heightDiff, offsetX, offsetY;
    private double width, height;
    private double lifetime;
    private final double initLifetime;
    private Entity target;
    private final boolean grow;
    
    public Effect(double x, double y, double initWidth, double initHeight, double finalWidth, double finalHeight, double lifetime, String imagePath) {
        super(x, y);
        this.widthDiff = finalWidth - initWidth;
        this.heightDiff = finalHeight - initHeight;
        this.width = initWidth;
        this.height = initHeight;
        this.lifetime = lifetime;
        initLifetime = lifetime;
        this.imagePath = imagePath;
        this.offsetX = 0;
        this.offsetY = 0;
        if(initWidth == finalWidth && initHeight == finalHeight){
            grow = false;
        }else{
            grow = true;
        }
    }
    public Effect(double x, double y, double initSize, double finalSize, double lifetime, String imagePath) {
        this(x, y, initSize, initSize, finalSize, finalSize, lifetime, imagePath);
    }
    
    public Effect(double x, double y, double initWidth, double initHeight, double finalWidth, double finalHeight, double lifetime, String imagePath, Entity target, double offsetX, double offsetY) {
        super(target.x+offsetX, target.y+offsetY);
        this.imagePath = imagePath;
        this.target = target;
        this.widthDiff = finalWidth - initWidth;
        this.heightDiff = finalHeight - initHeight;
        this.width = initWidth;
        this.height = initHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.initLifetime = lifetime;
        if(initWidth == finalWidth && initHeight == finalHeight){
            grow = false;
        }else{
            grow = true;
        }
    }
    
    public Effect(double x, double y, double initSize, double finalSize, double lifetime, String imagePath, Entity target, double offsetX, double offsetY) {
        this(x, y, initSize, initSize, finalSize, finalSize, lifetime, imagePath, target, offsetX, offsetY);
    }

    @Override
    public void draw(DrawHelper draw) {
        if (img == null) {
            try {
                img = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(imagePath));
            } catch (IOException ex) {
                Logger.getLogger(TeslaTower.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        draw.drawImage(x, y, width, height, img, 0);
    }

    @Override
    public void tick(double deltaTime) {
        lifetime -= deltaTime;
        if(lifetime <= 0){
            GamePanel.instance().removeEntity(this);
            return;
        }
        if(grow){
            double timeFactor = deltaTime/initLifetime;
            width += timeFactor * widthDiff;
            height += timeFactor * heightDiff;
        }
        if(target != null){
            x = target.x + offsetX;
            y = target.y + offsetY;
        }
    }
    
}
