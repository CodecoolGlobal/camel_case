package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Key;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attackDamage = 2;

    public Actor(Cell cell, int attackDamage) {
        this.cell = cell;
        this.attackDamage = attackDamage;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell != null) {
            if (validateCell(nextCell) && nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public boolean isEnemyNearby() {
        int[][] neighborCells = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
        Cell nextCell;
        for (int[] coordinateDifference : neighborCells) {
            if (cell.getNeighbor(coordinateDifference[0], coordinateDifference[1]).getActor() != null) {
                    nextCell = cell.getNeighbor(coordinateDifference[0], coordinateDifference[1]);
                    attack(nextCell, this.attackDamage);
                    return true;
            }
        }
        return false;
    }

    public void attack(Cell cell, int attackDamage) {
        cell.getActor().setHealth(attackDamage);
    }

    public void setHealth(int attackDamage) {
        this.health = this.health - attackDamage;
        if (this.health > 0){
            this.cell.getActor().isEnemyNearby();
        }
        System.out.println("Character: " + this.getTileName());
        System.out.println("HP left: " + this.health);
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean validateCell(Cell cell) {
        return cell.getType().toString().equals("FLOOR");
    }
}
