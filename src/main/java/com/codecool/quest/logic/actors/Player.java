package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Sword;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor {

    public int key = 0;
    public int sword = 0;

    boolean godMode;
    String name;
    public ListView<Item> inventory;

    public Player(Cell cell, ListView<Item> inventory, int health, int attackDamage) {
        super(cell);
        this.inventory = inventory;
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }


    public String getTileName() {
        return "player";
    }

    public void setGodMode(String name) {

        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        this.godMode = developerNames.contains(name);
    }

    public boolean isGodMode() {
        return this.godMode;
    }

    public void addItemToInventory(Item item) {
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


}
