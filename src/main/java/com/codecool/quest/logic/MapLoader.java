package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Ghost;
import com.codecool.quest.logic.actors.Knight;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Trap;
import com.codecool.quest.logic.items.*;
import javafx.scene.control.ListView;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    static private Player savedPlayer = null;

    public static void setSavedPlayer(Player player) {
        savedPlayer = player;
    }

    public static GameMap loadMap(String mapName) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapName + ".txt");

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        int count = 0;
        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);

        ListView<Item> inventory = new ListView<>();
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case 'r':
                            cell.setType(CellType.WINDOW);
                            break;
                        case 'b':
                            cell.setType(CellType.BUSH);
                            break;
                        case 'B':
                            cell.setType(CellType.GREENBUSH);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell, 5, 2, 1);
                            break;
                        case '@':
                            System.out.println(savedPlayer == null);
                            cell.setType(CellType.FLOOR);
                            if(savedPlayer != null) {
                                map.setPlayer(new Player(cell, savedPlayer.getInventory(), savedPlayer.getHealth(), savedPlayer.getAttackDamage()));
                            } else {
                                map.setPlayer(new Player(cell, inventory, 10, 2));
                            }
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell, 2);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell, 4, 4, 3);
                            break;
                        case 'l':
                            cell.setType(CellType.FLOOR);
                            new Knight(cell, 10, 7);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            new Trap(cell, 1);
                            break;
                        case 'd':
                            cell.setType(CellType.WALL);
                            new Door(cell);
                            break;
                        case 'y':
                            cell.setType(CellType.FLOOR);
                            new Portal(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
