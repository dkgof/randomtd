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
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

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

    private BufferedImage background;
    private BufferedImage pathMask;
    private BufferedImage towerMask;

    private Rectangle2D.Double spawnRect;
    private Rectangle2D.Double deSpawnRect;

    private ArrayList<Vector2D> npcPath;

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

            spawnRect = findHotspot(this.pathMask, new Color(0, 255, 0));
            deSpawnRect = findHotspot(this.pathMask, new Color(255, 0, 0));

            npcPath = findPath(spawnRect, deSpawnRect, this.pathMask.getRaster().getPixels(0, 0, this.pathMask.getWidth(), this.pathMask.getHeight(), (int[]) null));
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

        removeEntities.stream().forEach((entity) -> {
            entity.destroyBuffs();
        });
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

        draw.drawDebug(spawnRect, Color.green);
        draw.drawDebug(deSpawnRect, Color.red);

        lastTime = System.nanoTime();
    }

    public void addEntity(Entity e) {
        addEntities.add(e);
    }

    public void removeEntity(Entity e) {
        e.setIsActive(false);
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

    private ArrayList<Vector2D> findPath(Rectangle2D.Double start, Rectangle2D.Double end, int[] rgbPixels) {
        int[][] pixels = new int[this.pathMask.getWidth()][this.pathMask.getHeight()];

        for (int i = 0; i < pixels.length; i++) {
            Arrays.fill(pixels[i], -1);
        }

        double pixelsPerMeter = this.pathMask.getWidth() / Graphics2DDrawHelper.GAME_SIZE_IN_METERS;

        int currentLength = 0;

        pixels[(int) (getStartX() * pixelsPerMeter)][(int) (getStartY() * pixelsPerMeter)] = currentLength;

        List<Vector2D> pixelsToCheck = new ArrayList<>();
        pixelsToCheck.add(new Vector2D((int) (getStartX() * pixelsPerMeter), (int) (getStartY() * pixelsPerMeter)));

        while (!pixelsToCheck.isEmpty()) {
            pixelsToCheck = bangPixels(pixels, pixelsToCheck, currentLength, rgbPixels);
            currentLength++;
        }

        ArrayList<Vector2D> path = new ArrayList<>();

        path.add(new Vector2D(end.x + end.width / 2.0, end.y + end.height / 2.0));
        int nextX = (int) (getEndX() * pixelsPerMeter);
        int nextY = (int) (getEndY() * pixelsPerMeter);

        currentLength = pixels[nextX][nextY];

        int counter = 0;

        while (currentLength > 0) {
            outerLoop:
            for (int x = nextX - 1; x < nextX + 2; x++) {
                for (int y = nextY - 1; y < nextY + 2; y++) {
                    try {
                        if (pixels[x][y] == currentLength - 1) {
                            currentLength = pixels[x][y];
                            nextX = x;
                            nextY = y;
                            counter++;

                            if (counter == 50) {
                                path.add(new Vector2D(nextX / (double) this.pathMask.getWidth() * Graphics2DDrawHelper.GAME_SIZE_IN_METERS, nextY / (double) this.pathMask.getHeight() * Graphics2DDrawHelper.GAME_SIZE_IN_METERS));
                                counter = 0;
                            }

                            break outerLoop;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }

        path.add(new Vector2D(start.x + start.width / 2.0, start.y + start.height / 2.0));

        Collections.reverse(path);

        return path;
    }

    private Rectangle2D.Double findHotspot(BufferedImage mask, Color maskColor) {
        double spawnMinX = Integer.MAX_VALUE;
        double spawnMaxX = Integer.MIN_VALUE;
        double spawnMinY = Integer.MAX_VALUE;
        double spawnMaxY = Integer.MIN_VALUE;

        for (int y = 0; y < mask.getWidth(); y++) {
            for (int x = 0; x < mask.getWidth(); x++) {
                int rgb = mask.getRGB(x, y);

                Color c = new Color(rgb);

                if (c.equals(maskColor)) {
                    spawnMaxX = Math.max(spawnMaxX, x);
                    spawnMinX = Math.min(spawnMinX, x);
                    spawnMaxY = Math.max(spawnMaxY, y);
                    spawnMinY = Math.min(spawnMinY, y);
                }
            }
        }

        spawnMinX /= mask.getWidth();
        spawnMaxX /= mask.getWidth();

        spawnMinY /= mask.getHeight();
        spawnMaxY /= mask.getHeight();

        double metersPerPixel = Graphics2DDrawHelper.GAME_SIZE_IN_METERS / this.getWidth();

        spawnMinX *= this.getWidth() * metersPerPixel;
        spawnMaxX *= this.getWidth() * metersPerPixel;

        spawnMinY *= this.getHeight() * metersPerPixel;
        spawnMaxY *= this.getHeight() * metersPerPixel;

        return new Rectangle2D.Double(spawnMinX, spawnMinY, spawnMaxX - spawnMinX, spawnMaxY - spawnMinY);
    }

    public ArrayList<Vector2D> getNpcPath() {
        return npcPath;
    }

    public double getStartX() {
        return spawnRect.getX() + spawnRect.getWidth() / 2.0;
    }

    public double getStartY() {
        return spawnRect.getY() + spawnRect.getHeight() / 2.0;
    }

    public double getEndX() {
        return deSpawnRect.getX() + deSpawnRect.getWidth() / 2.0;
    }

    public double getEndY() {
        return deSpawnRect.getY() + deSpawnRect.getHeight() / 2.0;
    }

    private List<Vector2D> bangPixels(int[][] pixels, List<Vector2D> pixelsToCheck, int currentLength, int[] rgbPixels) {
        List<Vector2D> newPixelsToCheck = new ArrayList<>();
        
        for(Vector2D pixelToCheck : pixelsToCheck) {
            if(pixelToCheck != null) {
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() - 1, (int)pixelToCheck.getY() - 1, currentLength, pixels, rgbPixels));
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX(), (int)pixelToCheck.getY() - 1, currentLength, pixels, rgbPixels));
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() + 1, (int)pixelToCheck.getY() - 1, currentLength, pixels, rgbPixels));

                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() - 1, (int)pixelToCheck.getY(), currentLength, pixels, rgbPixels));
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() + 1, (int)pixelToCheck.getY(), currentLength, pixels, rgbPixels));

                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() - 1, (int)pixelToCheck.getY() + 1, currentLength, pixels, rgbPixels));
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX(), (int)pixelToCheck.getY() + 1, currentLength, pixels, rgbPixels));
                newPixelsToCheck.add(updatePixel((int)pixelToCheck.getX() + 1, (int)pixelToCheck.getY() + 1, currentLength, pixels, rgbPixels));
            }
        }
        
        return newPixelsToCheck;
    }

    private Vector2D updatePixel(int x, int y, int currentLength, int[][] pixels, int[] rgbPixels) {
        try {
            int foundLength = pixels[x][y];

            if (foundLength == -1) {
                foundLength = Integer.MAX_VALUE;
            }

            int r = rgbPixels[y * pixels.length * 4 + x * 4];
            int g = rgbPixels[y * pixels.length * 4 + x * 4 + 1];
            int b = rgbPixels[y * pixels.length * 4 + x * 4 + 2];
            
            if (!(r == 255 && g == 255 && b == 255) && currentLength + 1 < foundLength) {
                pixels[x][y] = currentLength + 1;
                return new Vector2D(x, y);
            }
        } catch (Exception e) {

        }

        return null;
    }
}
