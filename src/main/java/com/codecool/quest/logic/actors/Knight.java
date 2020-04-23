package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Actor{
    static List<Knight> knightList = new ArrayList<>();

    public Knight(Cell cell, int health, int attackDamage) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        knightList.add(this);
    }



    public static List<Knight> getKnightList() {
        return knightList;
    }
}
