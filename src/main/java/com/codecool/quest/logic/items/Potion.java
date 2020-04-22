package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Util;

public class Potion extends Item {
    int healAmount;

    public Potion(Cell cell) {
        super(cell);
        this.healAmount = Util.getRandomNumber(1, 2);
    }

    public int getHealAmount() {
        return healAmount;
    }

    public String getTileName(){
        return this.getClass().getSimpleName().toLowerCase() + this.healAmount;
    }

}
