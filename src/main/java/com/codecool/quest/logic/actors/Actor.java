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

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell != null) {
            if (isFloorCell(nextCell) && nextCell.getActor() == null) {
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

    public void updateHealth(int attackDamage) {
        this.health = this.health - attackDamage;
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

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isFloorCell(Cell cell) {
        return cell.getType().toString().equals("FLOOR");
    }
}