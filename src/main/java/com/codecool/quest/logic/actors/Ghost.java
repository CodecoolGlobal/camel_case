package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

import java.util.ArrayList;
import java.util.List;

public class Ghost extends Actor{
    static List<Ghost> ghostList = new ArrayList<>();

    public Ghost(Cell cell, int health, int attackDamage, int steps) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        ghostList.add(this);
        this.setSteps(steps);
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

    public void smartAutomove(int[] playerPos) {
        int steps = this.getSteps();

        int[] skeletonPos = {this.getCell().getX(), this.getCell().getY()};

        int[][] possibleCoords = {{skeletonPos[0] + steps, skeletonPos[1]}, {skeletonPos[0] - steps, skeletonPos[1]}, {skeletonPos[0], skeletonPos[1] + steps}, {skeletonPos[0], skeletonPos[1] - steps}};

        int[][] possibleSteps = {{steps, 0}, {-steps, 0}, {0, steps}, {0, -steps}};

        double min = 9999;
        int indexOfSmallest = 0;


        for (int i = 0; i < possibleSteps.length; i++) {
            if (this.getCell().getNeighbor(possibleSteps[i][0], possibleSteps[i][1]).getActor() == null) {
                if (distanceBetweenPos(possibleCoords[i], playerPos) < min || min == 0) {
                    min = distanceBetweenPos(possibleCoords[i], playerPos);
                    indexOfSmallest = i;
                }
            }
        }

        move(possibleSteps[indexOfSmallest][0], possibleSteps[indexOfSmallest][1], false);


    }

    private double distanceBetweenPos(int[] playerPos, int[] enemyPos) {
        return Math.sqrt(Math.pow((enemyPos[0] - playerPos[0]-1), 2) + Math.pow((enemyPos[0] - playerPos[0]-1), 2));
    }

    public static List<Ghost> getGhostList() {
        return ghostList;
    }

}
