package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.items.Sword;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(map.getWidth() * Tiles.TILE_WIDTH, map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();


    Label swordLabel = new Label();
    Label keyLabel = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        int rowIndex = 0;


        Label nameLbl = new Label("Name: ");
        Label inventoryLbl = new Label("Inventory: ");
        TextField nameInput = new TextField();
        Button b = new Button("Submit");
        b.setOnAction((e -> {
            map.getPlayer().setGodMode(nameInput.getText());
            nameInput.setDisable(true);
            b.setDisable(true);
        }));

        String IMAGE_FOLDER = System.getenv("IMAGE_FOLDER");
        FileInputStream input = new FileInputStream(IMAGE_FOLDER + "/key.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        FileInputStream input2 = new FileInputStream(IMAGE_FOLDER + "/sword.png");
        Image image2 = new Image(input2);
        ImageView imageView2 = new ImageView(image2);

        healthLabel.setTextFill(Color.WHITE);
        healthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        keyLabel.setTextFill(Color.WHITE);
        keyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        swordLabel.setTextFill(Color.WHITE);
        swordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLbl.setTextFill(Color.WHITE);
        nameLbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        inventoryLbl.setTextFill(Color.WHITE);
        inventoryLbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ui.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ui.add(nameLbl, 0, rowIndex++);
        ui.add(nameInput, 0, rowIndex++);
        ui.add(b, 0 ,rowIndex++);
        ui.setVgap(30);
        ui.add(healthLabel, 0, rowIndex++);
        ui.setVgap(20);
        ui.add(inventoryLbl, 0, rowIndex++);
        ui.add(imageView, 0, rowIndex);
        ui.add(keyLabel, 1, rowIndex++);
        ui.add(imageView2,0, rowIndex);
        ui.add(swordLabel, 1, rowIndex);



        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1, map.getPlayer().isGodMode());
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1, map.getPlayer().isGodMode());
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0, map.getPlayer().isGodMode());
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0, map.getPlayer().isGodMode());
                refresh();
                break;
            case SPACE:
                handleAttack();
                refresh();
                break;
        }
    }

    private void handleAttack() {
        Cell cellToAttack = map.getPlayer().getNeighborEnemyCell();
        if (cellToAttack != null) {
            map.getPlayer().attack(cellToAttack);
            if (cellToAttack.getActor().getHealth() > 0) {
                Cell playerCell = map.getPlayer().getCell();
                cellToAttack.getActor().attack(playerCell);
            }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getActor().getHealth() > 0) {
                        Tiles.drawTile(context, cell.getActor(), x, y);
                    } else {
                        cell.removeActor();
                        Tiles.drawTile(context, cell, x, y);
                    }
                    if (cell.getItem() != null && cell.getItem().getTileName().equals("sword")) {
                        Sword sword = (Sword) cell.getItem();
                        map.getPlayer().addWeaponToInventory(sword);
                        cell.removeItem();
                    } else if (cell.getItem() != null) {
                        map.getPlayer().addItemToInventory(cell.getItem());
                        cell.removeItem();
                    }
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        swordLabel.setText("" + map.getPlayer().sword);
        keyLabel.setText("" + map.getPlayer().key);
    }
}
