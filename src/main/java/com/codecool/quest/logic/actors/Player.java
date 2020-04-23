package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.items.Door;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
import com.codecool.quest.logic.items.Sword;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor {
    private boolean godMode;

    private ListView<Item> inventory;

    public Player(Cell cell, ListView<Item> inventory, int health, int attackDamage) {
        super(cell);
        this.inventory = inventory;
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }

    public ListView<Item> getInventory() {
        return inventory;
    }

    public void setGodMode(String name) {
        List<String> developerNames = new ArrayList<>(Arrays.asList("Edit", "Vivien", "Tomi", "Kornél"));
        this.godMode = developerNames.contains(name);
    }

    public boolean isGodMode() {
        return this.godMode;
    }

    public void addKeyToInventory(Item item) {
        this.inventory.getItems().add(item);
    }

    public void addWeaponToInventory(Sword sword) {
        this.inventory.getItems().add(sword);
        updateAttackDamage(sword.getAttackDamage());
    }

    public void updateAttackDamage(int damageModifier) {
        this.setAttackDamage(this.getAttackDamage() + damageModifier);
    }


    public int getKey() {
        int count = 0;
        for (Item item : this.inventory.getItems()) {
            if (item.getType().equals("key")) {
                count++;
            }
        }
        return count;
    }

    public void removeKey(int id) {
        ObservableList<Item> itemArrayList = inventory.getItems();
        inventory = new ListView<>();
        for (Item item : itemArrayList) {
            System.out.println(item.getType());
            if (item.getType().equals("key")) {
                Key key = (Key) item;
                if (key.getId() != id) {
                    this.addKeyToInventory(item);
                }
            } else {
                Sword sword = (Sword) item;
                this.addWeaponToInventory(sword);
            }
        }
    }

    public int getSword() {
        int count = 0;
        for (Item item : this.inventory.getItems()) {
            if (item.getType().equals("sword")) {
                count++;
            }
        }
        return count;
    }

    public boolean hasKey(int id) {
        for (Item item : this.inventory.getItems()) {
            if (item.getType().equals("key")) {
                Key key = (Key) item;
                if (key.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }


    public Door openDoor() {
        int[][] coordinateModifiers = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] modifier : coordinateModifiers) {
            try {
                if (this.getCell().getNeighbor(modifier[0], modifier[1]).getItem().getType().equals("door")) {
                    Door door = (Door) this.getCell().getNeighbor(modifier[0], modifier[1]).getItem();
                    if (hasKey(door.getId())) {
                        this.getCell().getNeighbor(modifier[0], modifier[1]).setType(CellType.FLOOR);
                        door.setOpen(true);

                        return door;
                    }
                }
            } catch (NullPointerException ignored) {
            }

        }
        return null;
    }
}
