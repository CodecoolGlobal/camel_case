package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Portal extends Item{
    private static int counter = 2;

    public Portal(Cell cell){
        super(cell);
    }

    public String getMapName(){
        return "map" + counter++;
    }
}
