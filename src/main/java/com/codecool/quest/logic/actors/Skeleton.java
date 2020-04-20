package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

import java.util.ArrayList;
import java.util.List;

public class Skeleton extends Actor {


    static List<Skeleton> skeletonList = new ArrayList<>();

    public Skeleton(Cell cell, int health, int attackDamage) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        skeletonList.add(this);
    }


    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void autoMove(){
        String direction = Util.getRandomDirection();
        assert direction != null;
        switch (direction){
            case "UP":
                move(0, 1, false);
                break;
            case "DOWN":
                move(0,-1,false);
                break;
            case "LEFT":
                move(-1, 0, false);
                break;
            case "RIGHT":
                move(1,0,false);
                break;
        }

    }

    public static List<Skeleton> getSkeletonList() {
        return skeletonList;
    }


}
