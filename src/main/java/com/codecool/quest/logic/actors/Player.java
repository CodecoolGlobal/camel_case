package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Sword;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor {
    private int potion = 0;
    private int key = 0;
    private int sword = 0;
    private boolean godMode;
    private String name;
    private ListView<Item> inventory;

    public Player(Cell cell, ListView<Item> inventory, int health, int attackDamage) {
        super(cell);
        this.inventory = inventory;
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }

    public void setGodMode(String name) {
        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        this.godMode = developerNames.contains(name);
    }

    public boolean isGodMode() {
        return this.godMode;
    }
    public void addPotionToInventory(Item item){
        this.inventory.getItems().add(item);
        this.potion += 1;
    }

    public void addKeyToInventory(Item item) {
        this.inventory.getItems().add(item);
        this.key += 1;
    }

    public void addWeaponToInventory(Sword sword) {
        this.inventory.getItems().add(sword);
        this.sword += 1;
        updateAttackDamage(sword.getAttackDamage());
    }

    public void updateAttackDamage(int damageModifier) {
        this.setAttackDamage(this.getAttackDamage() + damageModifier);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getSword() {
        return sword;
    }

    public void setSword(int sword) {
        this.sword = sword;
    }
    public int getPotion(){
        return this.potion;
    }

}
