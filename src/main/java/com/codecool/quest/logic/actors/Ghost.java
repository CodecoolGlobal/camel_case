package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

import java.util.ArrayList;
import java.util.List;

public class Ghost extends Actor{
    static List<Ghost> ghostList = new ArrayList<>();

    public Ghost(Cell cell, int health, int attackDamage) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        ghostList.add(this);
    }

    public void autoMove(){
        String direction = Util.getRandomDirection();
        assert direction != null;
        switch (direction){
            case "UP":
                move(0, 3, false);
                break;
            case "DOWN":
                move(0,-3,false);
                break;
            case "LEFT":
                move(-3, 0, false);
                break;
            case "RIGHT":
                move(3,0,false);
                break;
        }

    }

    public static List<Ghost> getGhostList() {
        return ghostList;
    }

}
