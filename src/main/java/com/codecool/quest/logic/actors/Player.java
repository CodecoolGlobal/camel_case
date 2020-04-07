package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Sword;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor {

    boolean godMode;
    String name;
    public ArrayList<Item> inventory;

    public Player(Cell cell, ArrayList<Item> inventory) {
        super(cell);
        this.inventory = inventory;

    }

    public String getTileName() {
        return "player";
    }

    public boolean validateMode(String name) {
        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        return developerNames.contains(name);
    }

    public void addItem(Item item){
        System.out.println(item);
        System.out.println(this.inventory);
        this.inventory.add(item);
    }

    public void deleteItem(Item item){
        this.inventory.remove(item);
    }
}
