/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author rolf
 */
public class Graphics2DDrawHelper implements DrawHelper {

    private static final double GAME_SIZE_IN_METERS = 100;
    
    private final Graphics2D g2;
    private final double pixelsPerMeterWidth;
    private final double pixelsPerMeterHeight;

    public Graphics2DDrawHelper(Graphics2D g2, int width, int height) {
        this.g2 = g2;
        this.pixelsPerMeterWidth = width / GAME_SIZE_IN_METERS;
        this.pixelsPerMeterHeight = this.pixelsPerMeterWidth;
    }
    
    @Override
    public void drawImage(double x, double y, double width, double height, Image img, double angle) {
        double correctedWidth = width * pixelsPerMeterWidth;
        double correctedHeight = height * pixelsPerMeterHeight;
        
        double correctedX = (x - width/2.0) * pixelsPerMeterWidth;
        double correctedY = (y - height/2.0) * pixelsPerMeterHeight;
        
        AffineTransform transform = new AffineTransform();
        transform.translate(correctedX, correctedY);
        transform.rotate(angle);
        transform.scale(correctedWidth / img.getWidth(null), correctedHeight / img.getHeight(null));
        
        g2.drawImage(img, transform, null);
        
        //g2.drawImage(img, (int) correctedX, (int) correctedY, (int) correctedWidth, (int) correctedHeight, null);
    }

    @Override
    public void drawRectangle(double x, double y, double width, double height, Color color) {
        rectangle(x, y, width, height, color, false);
    }

    @Override
    public void fillRectangle(double x, double y, double width, double height, Color color) {
        rectangle(x, y, width, height, color, true);
    }

    private void rectangle(double x, double y, double w, double h, Color c, boolean fill) {
        double correctedWidth = w * pixelsPerMeterWidth;
        double correctedHeight = h * pixelsPerMeterHeight;
        
        double correctedX = (x - w/2.0) * pixelsPerMeterWidth;
        double correctedY = (y - h/2.0) * pixelsPerMeterHeight;
        
        g2.setColor(c);
        if(fill) {
            g2.fillRect((int) correctedX, (int) correctedY, (int) correctedWidth, (int) correctedHeight);
        } else {
            g2.drawRect((int) correctedX, (int) correctedY, (int) correctedWidth, (int) correctedHeight);
        }
    }
}
