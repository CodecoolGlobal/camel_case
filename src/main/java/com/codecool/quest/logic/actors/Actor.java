package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int attackDamage;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public String getTileName(){
        return this.getClass().getSimpleName().toLowerCase();
    }

    public void move(int dx, int dy, boolean godMode) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell != null) {
            if ((nextCell.isFloorCell() && nextCell.getActor() == null) || godMode) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public void attack(Cell cell) {
        cell.getActor().updateHealth(getAttackDamage());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void updateHealth(int injury) {
        this.health = this.health - injury;
        if (this.health > 0) {
            this.cell.getActor().getNeighborEnemyCell();
        }
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Cell getCell() {
        return cell;
    }

    public Cell getNeighborEnemyCell() {
        int[][] coordinateModifiers = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
        for (int[] modifier : coordinateModifiers) {
            if (cell.getNeighbor(modifier[0], modifier[1]).getActor() != null) {
                return cell.getNeighbor(modifier[0], modifier[1]);
            }
        }
        return null;
    }


}