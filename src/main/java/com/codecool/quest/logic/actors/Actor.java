package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Key;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int attackDamage;

    public Actor(Cell cell) {
        this.cell = cell;
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

    public Cell isEnemyNearby() {
        int[][] neighborCells = { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
        Cell nextCell;
        for (int[] coordinateDifference : neighborCells) {
            if (cell.getNeighbor(coordinateDifference[0], coordinateDifference[1]).getActor() != null) {
                    return cell.getNeighbor(coordinateDifference[0], coordinateDifference[1]);
            }
        }
        return null;
    }

    public void attack(Cell cell) {
        cell.getActor().updateHealth(this.attackDamage);
    }

    public void updateHealth(int attackDamage) {
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

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getAttackDamage(){
        return this.attackDamage;
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public int getY() {
        return cell.getY();
    }

    public boolean validateCell(Cell cell) {
        return cell.getType().toString().equals("FLOOR");
    }
}
