package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;


public class Key extends Item {
    private int id;

    private static int counter = 0;

    public Key(Cell cell){
        super(cell);
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    public String getTileName(){
        return this.getClass().getSimpleName().toLowerCase() + this.id;
    }
}
