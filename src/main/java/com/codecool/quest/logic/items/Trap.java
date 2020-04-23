package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Trap extends Item {
    int damage;

    public Trap(Cell cell, int damage) {
        super(cell);
        this.damage = damage;

    }

    public int getDamage() {
        return this.damage;
    }
}
