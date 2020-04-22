package com.codecool.quest.logic;

import java.util.Random;

public class Util {
    static Random random = new Random();

    static public int getRandomNumber(int min, int max){
        max++;
        return min + random.nextInt(max - min);
    }

    static public String getRandomDirection(){
        int direction = getRandomNumber(0, 3);
        switch (direction){
            case 0:
                return "UP";
            case 1:
                return "DOWN";
            case 2:
                return "LEFT";
            case 3:
                return "RIGHT";
        }
        return null;
    }
}
