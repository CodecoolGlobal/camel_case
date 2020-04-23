package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Skeleton extends Actor {


    static List<Skeleton> skeletonList = new ArrayList<>();

    public Skeleton(Cell cell, int health, int attackDamage, int steps) {
        super(cell);
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
        skeletonList.add(this);
        this.setSteps(steps);
    }

    public void autoMove() {
        String direction = Util.getRandomDirection();
        assert direction != null;
        switch (direction) {
            case "UP":
                move(0, 1, false);
                break;
            case "DOWN":
                move(0, -1, false);
                break;
            case "LEFT":
                move(-1, 0, false);
                break;
            case "RIGHT":
                move(1, 0, false);
                break;
        }

    }

    public static List<Skeleton> getSkeletonList() {
        return skeletonList;
    }

    public void smartAutoMove(int[] playerPos) {
        int steps = this.getSteps();
        int[] skeletonPos = {this.getCell().getX(), this.getCell().getY()};
        int[][] possibleCoords = {{skeletonPos[0] + steps, skeletonPos[1]}, {skeletonPos[0] - steps, skeletonPos[1]}, {skeletonPos[0], skeletonPos[1] + steps}, {skeletonPos[0], skeletonPos[1] - steps}};
        int[][] possibleSteps = {{steps, 0}, {-steps, 0}, {0, steps}, {0, -steps}};

        double min = 0;
        int indexOfSmallest = 0;
        int distance = 5;
        boolean canSmartMove = false;
        for (int i = 0; i < possibleSteps.length; i++) {
            if (this.getCell().getNeighbor(possibleSteps[i][0], possibleSteps[i][1]).getActor() == null) {
                if (distanceBetweenPos(possibleCoords[i], playerPos) <= distance) {
                    if (distanceBetweenPos(possibleCoords[i], playerPos) < min || min == 0) {
                        min = distanceBetweenPos(possibleCoords[i], playerPos);
                        indexOfSmallest = i;
                        canSmartMove = true;
                    }
                }
            }
        }
        if (canSmartMove){
            move(possibleSteps[indexOfSmallest][0], possibleSteps[indexOfSmallest][1], false);
        } else {
            autoMove();
        }
    }

    private double distanceBetweenPos(int[] playerPos, int[] enemyPos) {
        return abs(playerPos[0] - enemyPos[0]) + abs(playerPos[1] - enemyPos[1]);
        // return Math.sqrt(Math.pow((enemyPos[0] - playerPos[0]), 2) + Math.pow((enemyPos[0] - playerPos[0]), 2));
    }
}
