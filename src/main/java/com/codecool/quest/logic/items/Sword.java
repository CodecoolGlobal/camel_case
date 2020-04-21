package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Sword extends Item {

    private int attackDamage;

    public Sword(Cell cell, int attackDamage){
        super(cell);
        this.setAttackDamage(attackDamage);
    }

    public void setAttackDamage(int attackDamage){
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage(){
        return this.attackDamage;
    }
}
