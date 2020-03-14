package Sapper.Controler;

import Sapper.View.Game;
import Sapper.View.Menu;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow {

    public static final double width = 1024;
    public static final double height = 768 ;

    private Stage stage;
    private Scene gameView, menuView;
    private Game game;
    private Menu menu;

    public Board basicBoard = new Board(PrecraftedLevel.BASIC.getLevel());
    public Board advancedBoard = new Board(PrecraftedLevel.ADVANCED.getLevel());
    public Board expertBoard = new Board(PrecraftedLevel.EXPERT.getLevel());
    public Board customBoard;

    public GameWindow(Stage stage){
        this.stage = stage;
    }

    public void build(){
        game = new Game(this);
        menu = new Menu(this );
        game.build();
        menu.build();
        menuView = new Scene(menu.getRoot(), width, height);
        gameView = new Scene(game.getRoot(),width,height);

        gameView.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        menuView.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        basicBoard.setGameWindow(this);
        advancedBoard.setGameWindow(this);
        expertBoard.setGameWindow(this);
        stage.setResizable(false);
        stage.setScene(menuView);
        stage.setOnCloseRequest(e ->{
            exitGame();
        });
        stage.show();
    }

    private void prepareBoard(Board board){
        board.clearBoard();
        updateBombAppearance();
    }

    public void handleBasicButton(){
        prepareBoard(basicBoard);
        game.showBasic();
        stage.setScene(gameView);
    }
    public void handleAdvancedButton(){
        prepareBoard(advancedBoard);
        game.showAdvanced();
        stage.setScene(gameView);
    }
    public void handleExpertButton(){
        prepareBoard(expertBoard);
        game.showExpert();
        stage.setScene(gameView);
    }

    private void updateBombAppearance(){
        switch(menu.getSelectedRadioButton().getText()){
            case "Random":
                BoardFiller.setBombAppearance(BombAppearance.POSSIBLE_AT_FIRST);
                break;
            case "No bomb":
                BoardFiller.setBombAppearance(BombAppearance.IMPOSSIBLE_AT_FIRST);
                break;
            case "Zero field":
                BoardFiller.setBombAppearance(BombAppearance.ZERO_FIELD_AT_FIRST);
                break;
        }
    }
    public void handleCustomButton(){
        menu.showCustomBox();
    }

    public void handleMenuButton(){
        stage.setScene(menuView);
    }

    public void handleRestartButton(){
        prepareBoard(game.getCurrentBoard());
    }

    public void handlePlayCustomButton(int horizontal,int vertical,int mines){
        Level customLevel = new Level(horizontal,vertical,mines);
        customBoard = new Board(customLevel);
        customBoard.setGameWindow(this);
        game.showCustom(customLevel);
        prepareBoard(customBoard);
        stage.setScene(gameView);
    }

    public void handleExitButton(){
        exitGame();
    }

    private void exitGame(){
        Platform.exit();
        System.exit(0);
    }

    void win(){
        game.showWinView();
    }

    void lost(){
        game.showGameOver();
    }
}

