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

    private final Graphics2D g2;

    public Graphics2DDrawHelper(Graphics2D g2) {
        this.g2 = g2;
    }
    
    @Override
    public void drawImage(double x, double y, double width, double height, Image img) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void drawRectangle(double x, double y, double width, double height, Color color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fillRectangle(double x, double y, double width, double height, Color color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
