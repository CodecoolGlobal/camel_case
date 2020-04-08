package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;

public class Skeleton extends Actor {

    private int healthPoints;

    public Skeleton(Cell cell, int healthPoints) {
        super(cell, 1);
        this.healthPoints = healthPoints;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
