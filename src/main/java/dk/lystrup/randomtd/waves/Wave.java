/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.waves;

import dk.lystrup.randomtd.domain.NPC;
import dk.lystrup.randomtd.domain.NPC.ArmorType;
import dk.lystrup.randomtd.npcs.BasicNPC;

/**
 *
 * @author Thor
 */
public class Wave {
    
    private final String name, model;
    private final double size, health, armor, movementSpeed;
    private final ArmorType armorType;
    private final double spawnInterval;
    private final int waveAmount;

    public Wave(String name, String model, double size, double health, double armor, double movementSpeed, ArmorType armorType, double spawnInterval, int waveAmount) {
        this.name = name;
        this.model = model;
        this.size = size;
        this.health = health;
        this.armor = armor;
        this.movementSpeed = movementSpeed;
        this.armorType = armorType;
        this.spawnInterval = spawnInterval;
        this.waveAmount = waveAmount;
    }
    
    public NPC createWaveNPC(){
        return new BasicNPC(name, model, size, health, armor, armorType, movementSpeed);
    }

    public int getWaveAmount() {
        return waveAmount;
    }

    public double getSpawnInterval() {
        return spawnInterval;
    }
}
