package Sapper.View;

import Sapper.Controler.Board;
import Sapper.Controler.GameWindow;
import Sapper.Controler.Level;
import Sapper.Controler.PrecraftedLevel;
import Sapper.Model.Position;
import Sapper.Model.Tile;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class Game {

    private GameWindow gameWindow;
    private Label time, bombs, winLoseLabel;
    private StackPane gameRoot, overlay;
    private BorderPane gameBorder;
    private GridPane basicView, advancedView, expertView, customView;
    private Board currentBoard;

    public Game(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public void build(){
        buildGameBorder();
        buildOverlay();
        basicView = buildGrid(PrecraftedLevel.BASIC.getLevel(), gameWindow.basicBoard);
        advancedView = buildGrid(PrecraftedLevel.ADVANCED.getLevel(), gameWindow.advancedBoard);
        expertView = buildGrid(PrecraftedLevel.EXPERT.getLevel(), gameWindow.expertBoard);
    }

    private void buildGameBorder(){
        HBox hbox = new HBox();
        Button menuButton = new Button("Menu");
        overlay = new StackPane();
        time = new Label("Time:");
        bombs = new Label("Bombs:");
        gameBorder = new BorderPane();
        time.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        bombs.setFont(Font.font("Calibri", FontWeight.BOLD, 20));

        menuButton.setPadding(new Insets(0,0,0,40));
        menuButton.setOnMouseClicked(e ->{
            gameWindow.handleMenuButton();
        });
        hbox.getChildren().addAll(time, bombs, menuButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(20));
        gameBorder.setTop(hbox);
        gameBorder.setStyle("-fx-background-color:"+ Style.backgroundColor);

        gameRoot = new StackPane(gameBorder);
    }

    private void buildOverlay(){
        Rectangle blackout = new Rectangle(GameWindow.width, GameWindow.height);
        Rectangle container = new Rectangle(250,300);
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        Button menuButton = new Button("Menu");
        Button restartButton = new Button("Restart");
        Label endTime = new Label();
        winLoseLabel = new Label();

        endTime.textProperty().bind(time.textProperty());
        endTime.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
        winLoseLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
        winLoseLabel.setPadding(new Insets(0,0,20,0));
        endTime.setPadding(new Insets(20,0,20,0));

        blackout.setFill(Color.web("#000000"));
        blackout.setOpacity(0.5);
        container.setFill(Color.web("#71AB12"));
        container.setArcHeight(25);
        container.setArcWidth(25);
        container.setStroke(Color.web("#1271AB"));
        container.setStrokeWidth(3);

        menuButton.setOnMouseClicked(e ->{
            removeOverlay();
            gameWindow.handleMenuButton();
        });
        restartButton.setOnMouseClicked(e ->{
            gameWindow.handleRestartButton();
            removeOverlay();
        });

        hbox.getChildren().addAll(menuButton,restartButton);
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.getChildren().addAll(winLoseLabel,endTime,hbox);

        overlay.getChildren().addAll(blackout,container, vbox);
    }

    private GridPane buildGrid(Level level, Board board){
        GridPane grid = new GridPane();
        int idCounter = 0;
        for(int i = 0; i < level.getBoardConstants().getHorizontalTiles(); i++){
            for(int j = 0; j < level.getBoardConstants().getVerticalTiles(); j++){
                idCounter++;
                Tile tile = new Tile(level, new Position(i, j));
                StackPane stack = new StackPane();
                stack.setStyle("-fx-background-color:"+ Style.defaultColor);
                tile.setId(String.valueOf(idCounter));
                board.addTile(tile);

                stack.getChildren().addAll(tile,tile.getDigit());
                grid.add(stack, i, j);
            }
        }
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private void addOverlay(){
        gameRoot.getChildren().add(overlay);
    }

    private void removeOverlay(){
        gameRoot.getChildren().remove(overlay);
    }

    public StackPane getRoot(){
        return gameRoot;
    }

    public void showBasic(){
        gameBorder.setCenter(basicView);
        attachTimeAndBombs(gameWindow.basicBoard);
        currentBoard = gameWindow.basicBoard;
    }

    public void showAdvanced(){
        gameBorder.setCenter(advancedView);
        attachTimeAndBombs(gameWindow.advancedBoard);
        currentBoard = gameWindow.advancedBoard;
    }
    public void showExpert(){
        gameBorder.setCenter(expertView);
        attachTimeAndBombs(gameWindow.expertBoard);
        currentBoard = gameWindow.expertBoard;
    }

    public void showCustom(Level level){
        customView = buildGrid(level, gameWindow.customBoard);
        gameBorder.setCenter(customView);
        attachTimeAndBombs(gameWindow.customBoard);
        currentBoard = gameWindow.customBoard;
    }

    private void attachTimeAndBombs(Board board){
        bombs.textProperty().bind(board.getBoardStatistics().marksLeftProperty().asString("Bombs: %d"));
        time.textProperty().bind(board.getBoardStatistics().secondsElapsedProperty().asString("Time: %d"));
    }

    public void showGameOver(){
        winLoseLabel.setText("You lost!");
        gameEnd();
    }

    public void showWinView(){
        winLoseLabel.setText("You won!");
        gameEnd();
    }

    private void gameEnd(){
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            gameRoot.setMouseTransparent(false);
            addOverlay();
        });
        gameRoot.setMouseTransparent(true);
        pause.play();
    }

    public Board getCurrentBoard(){
        return currentBoard;
    }
}
