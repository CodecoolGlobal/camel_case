package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;


public class Key extends Item {

    public Key(Cell cell){
        super(cell);
    }

    @Override
    public String getTileName(){
        return "key";
    }
}
