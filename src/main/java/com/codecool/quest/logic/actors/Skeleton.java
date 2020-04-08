package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Skeleton extends Actor {

    public Skeleton(Cell cell, int health, int attackDamage) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }
    
    @Override
    public String getTileName() {
        return "skeleton";
    }
}
