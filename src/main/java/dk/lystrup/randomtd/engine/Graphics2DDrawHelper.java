/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author rolf
 */
public class Graphics2DDrawHelper implements DrawHelper {

    private static final int GAME_SIZE_IN_METERS = 100;
    
    private final Graphics2D g2;
    private final double pixelsPerMeterWidth;
    private final double pixelsPerMeterHeight;

    public Graphics2DDrawHelper(Graphics2D g2, int width, int height) {
        this.g2 = g2;
        this.pixelsPerMeterWidth = width / GAME_SIZE_IN_METERS;
        this.pixelsPerMeterHeight = height / GAME_SIZE_IN_METERS;
    }
    
    @Override
    public void drawImage(double x, double y, double width, double height, Image img) {
    }

    @Override
    public void drawRectangle(double x, double y, double width, double height, Color color) {
        double correctedWidth = width * pixelsPerMeterWidth;
        double correctedHeight = height * pixelsPerMeterHeight;
        
        double correctedX = x * pixelsPerMeterWidth;
        double correctedY = y * pixelsPerMeterHeight;
        
        
    }

    @Override
    public void fillRectangle(double x, double y, double width, double height, Color color) {
    }

    private void rectangle(double x, double y, double w, double h, Color c, boolean fill) {
        g2.setColor(c);
        if(fill) {
            g2.drawRect((int) x, (int) y, (int) w, (int) h);
        } else {
            g2.fillRect((int) x, (int) y, (int) w, (int) h);
        }
    }
}
