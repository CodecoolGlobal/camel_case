package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;

        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("key0", new Tile(16, 23));
        tileMap.put("sword", new Tile(3, 29));
        tileMap.put("key1", new Tile(18, 23));
        tileMap.put("door1", new Tile(10, 9));
        tileMap.put("door2", new Tile(22, 11));
        tileMap.put("potion1", new Tile(18, 25));
        tileMap.put("potion2", new Tile(26, 23));
        tileMap.put("trap", new Tile(15, 10));
        tileMap.put("bush", new Tile(6, 2));
        tileMap.put("greenBush", new Tile(1, 2));
        tileMap.put("cross", new Tile(1, 14));
        tileMap.put("window", new Tile(1, 13));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("knight", new Tile(31, 0));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h, x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH,
                TILE_WIDTH);
    }
}
