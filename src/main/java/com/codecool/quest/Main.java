package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import com.codecool.quest.logic.actors.Ghost;
import com.codecool.quest.logic.actors.Skeleton;
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
import java.util.List;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(map.getWidth() * Tiles.TILE_WIDTH, map.getHeight() * Tiles.TILE_WIDTH);
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
        ui.add(swordLabel, 1, rowIndex);
    }

    private void setLabel(Label healthLabel) {
        healthLabel.setTextFill(Color.WHITE);
        healthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
    }

    private void onKeyPressed(KeyEvent keyEvent) {

        // skeleton movement
        autoMoveEnemies();

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
                        cell.setActor(null);
                        Tiles.drawTile(context, cell, x, y);
                    }
                    if (cell.getItem() != null && cell.getItem().getTileName().equals("sword") && cell.getActor().getTileName().equals("player")) {
                        Sword sword = (Sword) cell.getItem();
                        map.getPlayer().addWeaponToInventory(sword);
                        cell.setItem(null);
                    } else if (cell.getItem() != null && cell.getActor().getTileName().equals("player")) {
                        map.getPlayer().addItemToInventory(cell.getItem());
                        cell.setItem(null);
                    }
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        swordLabel.setText("" + map.getPlayer().getSword());
        keyLabel.setText("" + map.getPlayer().getKey());
    }

    private void autoMoveEnemies(){
        List<Skeleton> skeletonList = Skeleton.getSkeletonList();
        for (Skeleton skeleton : skeletonList) {
            skeleton.autoMove();
        }
        List<Ghost> ghostList = Ghost.getGhostList();
        for(Ghost ghost : ghostList){
            ghost.autoMove();
        }
    }
}
