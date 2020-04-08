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

    public int key = 0;
    public int sword = 0;

    boolean godMode;
    String name;
    public ArrayList<String> inventoryList = new ArrayList<String>();

    public Player(Cell cell, ArrayList<String> inventoryList) {
        super(cell);
        this.inventoryList = inventoryList;

    }


    public String getTileName() {
        return "player";
    }

    public boolean validateMode(String name) {
        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        return developerNames.contains(name);
    }

    public void addItem(Item item){
        this.inventoryList.add(item.getTileName());
        if (item.getTileName().equals("sword")){
            this.sword += 1;
        }else if (item.getTileName().equals("key")){
            this.key += 1;
        }
    }
/*
    public void deleteItem(Item item){
        this.inventory.remove(item);
    }

 */
}
