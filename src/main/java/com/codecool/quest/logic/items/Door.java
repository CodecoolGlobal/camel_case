package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;


public class Door extends Item {
    private int id;
    private Boolean isOpen;
    private static int counter = 0;

    public Door(Cell cell) {
        super(cell);
        this.id = counter;
        counter++;
        isOpen = false;
    }

    public int getId() {
        return id;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getTileName() {
        if (isOpen) return "opened" + this.getClass().getSimpleName();
        else return "closed" + this.getClass().getSimpleName();
    }
}
