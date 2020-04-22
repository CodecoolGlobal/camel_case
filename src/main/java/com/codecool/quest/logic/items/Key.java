package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;


public class Key extends Item {
    int id;

    public Key(Cell cell, int id){
        super(cell);
        this.id = id;
    }

    public String getTileName(){
        return this.getClass().getSimpleName().toLowerCase() + this.id;
    }
}
