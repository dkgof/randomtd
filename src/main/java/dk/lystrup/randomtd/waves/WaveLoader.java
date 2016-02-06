/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.waves;

import dk.cavi.xml.XMLLoader;
import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.NPC.ArmorType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Thor
 */
public class WaveLoader {
    public static List<Wave> parseWaves(String waveFile){
        final List<Wave> result = new ArrayList<>();
        
        try {
            XMLLoader loader = new XMLLoader(WaveLoader.class.getClassLoader().getResourceAsStream(waveFile));
            List<Node> nodes = loader.getElements("/waves/wave");
            nodes.stream().forEachOrdered((Node node) -> {
                String id = node.getAttributes().getNamedItem("id").getTextContent();
                String name = loader.getString("/waves/wave[@id='"+id+"']/name", "");
                String model = loader.getString("/waves/wave[@id='"+id+"']/model", "NPC_Default.png");
                if(!model.endsWith(".png")){
                    model = model + ".png";
                }
                double size = loader.getDouble("/waves/wave[@id='"+id+"']/size", 1);
                double health = loader.getDouble("/waves/wave[@id='"+id+"']/health", 1);
                double armor = loader.getDouble("/waves/wave[@id='"+id+"']/armor", 0);
                double movementSpeed = loader.getDouble("/waves/wave[@id='"+id+"']/movementSpeed", 1);
                double spawnInterval = loader.getDouble("/waves/wave[@id='"+id+"']/spawnInterval", 1);
                ArmorType armorType = ArmorType.valueOf(loader.getString("/waves/wave[@id='"+id+"']/armorType", "NONE"));
                int spawnAmount = loader.getInt("/waves/wave[@id='"+id+"']/amount", 1);
                result.add(new Wave(name, model, size, health, armor, movementSpeed, armorType, spawnInterval, spawnAmount));
            });
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException ex) {
            Logger.getLogger(WaveLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(result);
        
        return result;
    }
}
