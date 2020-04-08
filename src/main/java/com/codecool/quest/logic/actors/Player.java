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
    public ArrayList<String> inventory = new ArrayList<String>();

    public Player(Cell cell, ArrayList<String> inventory) {
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

    public void addItem(Item item){
        this.inventory.add(item.getTileName());
        if (item.getTileName().equals("sword")){
            this.sword += 1;
        }else if (item.getTileName().equals("key")){
            this.key += 1;
        }
    }
/*
    public void addWeaponToInventory(Sword sword) {
        this.inventory.add(sword);
        updateAttackDamage(sword.getAttackDamage());
    }

    public boolean isWeapon(Item item){
        return item.getTileName().equals("sword");
    }

    public void updateAttackDamage(int damageModifier){
        this.setAttackDamage(this.getAttackDamage() + damageModifier);
    }

    public void removeFromInventory(Item item) {
        this.inventory.remove(item);
    }
*/
    public ArrayList<String> getInventory() {
        return this.inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }



}
