package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;


public class Door extends Item {
    public int id;
    public Boolean isOpen;

    public Door(Cell cell, int id){

       super(cell);
        this.id = id;
        isOpen = false;
    }
}


