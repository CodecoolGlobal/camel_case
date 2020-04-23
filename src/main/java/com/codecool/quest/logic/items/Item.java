package com.codecool.quest.logic.items;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;


public abstract class Item implements Drawable{

    public Item(Cell cell){
        cell.setItem(this);
    }

    public String getType(){
        return this.getClass().getSimpleName().toLowerCase();
    }

    public String getTileName(){
        return this.getClass().getSimpleName().toLowerCase();
    }

}
