package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Cross;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Potion;
import com.codecool.quest.logic.items.Sword;
import com.codecool.quest.logic.items.Trap;
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
import java.util.List;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    int windowWidth = 10;
    int windowHeight = 10;
    Canvas canvas = new Canvas(windowWidth * Tiles.TILE_WIDTH, windowHeight * Tiles.TILE_WIDTH);
    Label healthLabel = new Label();
    Label swordLabel = new Label();
    Label keyLabel = new Label();
    GraphicsContext context = canvas.getGraphicsContext2D();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        GridPane ui = new GridPane();
        Label nameLabel = new Label("Name: ");
        Label inventoryLabel = new Label("Inventory: ");
        TextField nameInput = new TextField();
        Button b = getButton(nameInput);
        ImageView imageView = getImageView("key.png");
        ImageView imageView2 = getImageView("sword.png");
        setLabel(healthLabel);
        setLabel(keyLabel);
        setLabel(swordLabel);
        setLabel(nameLabel);
        setLabel(inventoryLabel);
        setUI(ui, nameLabel, inventoryLabel, healthLabel, keyLabel, swordLabel, nameInput, b, imageView, imageView2);
        setPrimaryStage(primaryStage, ui);
    }

    private void setPrimaryStage(Stage primaryStage, GridPane ui) {
        BorderPane borderPane = setBorderPane(ui);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();
    }

    private BorderPane setBorderPane(GridPane ui) {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        return borderPane;
    }

    private ImageView getImageView(String filename) throws FileNotFoundException {
        String IMAGE_FOLDER = System.getenv("IMAGE_FOLDER");
        FileInputStream inputStream = new FileInputStream(IMAGE_FOLDER + "/" + filename);
        Image image = new Image(inputStream);
        return new ImageView(image);
    }

    private Button getButton(TextField nameInput) {
        Button b = new Button("Submit");
        b.setOnAction((e -> {
            map.getPlayer().setGodMode(nameInput.getText());
            nameInput.setDisable(true);
            b.setDisable(true);
        }));
        return b;
    }

    private void setUI(GridPane ui, Label nameLbl, Label inventoryLbl, Label healthLabel, Label keyLabel, Label swordLabel, TextField nameInput, Button b, ImageView imageView, ImageView imageView2) {
        int rowIndex = 0;
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ui.add(nameLbl, 0, rowIndex++);
        ui.add(nameInput, 0, rowIndex++);
        ui.add(b, 0, rowIndex++);
        ui.setVgap(30);
        ui.add(healthLabel, 0, rowIndex++);
        ui.setVgap(20);
        ui.add(inventoryLbl, 0, rowIndex++);
        ui.add(imageView, 0, rowIndex);
        ui.add(keyLabel, 1, rowIndex++);
        ui.add(imageView2, 0, rowIndex);
        ui.add(swordLabel, 1, rowIndex++);
    }

    private void setLabel(Label healthLabel) {
        healthLabel.setTextFill(Color.WHITE);
        healthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        // skeleton movement
        List<Skeleton> skeletonList = Skeleton.getSkeletonList();
        for (Skeleton skeleton : skeletonList) {
            skeleton.autoMove();
        }


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
        int[] windowCenterCoordinates = getPlayerCoordinates();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int windowX = 0;
        int windowY = 0;
        for (int mapX = windowCenterCoordinates[0] - (windowWidth / 2); mapX <= windowCenterCoordinates[0] + (windowWidth / 2); mapX++) {
                for (int mapY = windowCenterCoordinates[1] - (windowHeight / 2); mapY <= windowCenterCoordinates[1] + (windowHeight / 2); mapY++) {
                    try {
                    Cell cell = map.getCell(mapX, mapY);
                    if (cell.getActor() != null) {
                        if (cell.getActor().getHealth() > 0) {
                            Tiles.drawTile(context, cell.getActor(), windowX, windowY++);
                        } else {
                            Cross cross = new Cross(cell);
                            map.getPlayer().setCell(null);
                            cell.setActor(cross);
                            Tiles.drawTile(context, cell.getActor(), windowX, windowY++);
                        }
                        if (cell.getItem() != null && cell.getItem().getTileName().equals("sword")) {
                            Sword sword = (Sword) cell.getItem();
                            map.getPlayer().addWeaponToInventory(sword);
                            cell.setItem(null);
                        } else if (cell.getItem() != null && cell.getItem().getType().equals("key")) {
                            map.getPlayer().addKeyToInventory(cell.getItem());
                            cell.setItem(null);
                        } else if (cell.getItem() != null && cell.getItem().getType().equals("potion")) {
                            map.getPlayer().addPotionToInventory(cell.getItem());
                            Potion healthPotion = (Potion) cell.getItem();
                            map.getPlayer().updateHealth(healthPotion.getHealAmount());
                            cell.setItem(null);
                        } else if (cell.getItem() != null && cell.getItem().getType().equals("trap")) {
                            Trap trap = (Trap) cell.getItem();
                            map.getPlayer().updateHealth(-trap.getDamage());
                        }
                    } else if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), windowX, windowY++);
                    } else {
                        Tiles.drawTile(context, cell, windowX, windowY++);
                    }
                }catch (IndexOutOfBoundsException e){
                        Cell cell = map.getCell(0, 0);
                        Tiles.drawTile(context, cell, windowX, windowY++);
                    }

            }
            windowY = 0;
            windowX++;

        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        swordLabel.setText("" + map.getPlayer().getSword());
        keyLabel.setText("" + map.getPlayer().getKey());
    }

    private int[] getPlayerCoordinates(){
        return new int[]{map.getPlayer().getCell().getX(), map.getPlayer().getCell().getY()};
    }
}
