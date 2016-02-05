/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.engine;

import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author rolf
 */
public interface DrawHelper {
    public void drawImage(double x, double y, double width, double height, Image img);
    
    public void drawRectangle(double x, double y, double width, double height, Color color);

    public void fillRectangle(double x, double y, double width, double height, Color color);
}