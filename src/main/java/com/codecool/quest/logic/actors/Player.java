package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor {

    private boolean godMode;
    private String name;
    private ListView<Item> inventory;

    public Player(Cell cell, ListView<Item> inventory, int health, int attackDamage) {
        super(cell);
        this.inventory = inventory;
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }

    public String getTileName() {
        return "player";
    }

    public boolean validateMode(String name) {
        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        return developerNames.contains(name);
    }

    public void addToInventory(Item item) {
        this.inventory.getItems().add(item);
    }

    public void removeFromInventory(Item item) {
        this.inventory.getItems().remove(item);
    }

    public ListView<Item> getInventory() {
        return this.inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
