package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;

public class Trap extends Item {
    int damage;
    public Trap(Cell cell, int damage){
        super(cell);
        this.damage = damage;

    }
}
