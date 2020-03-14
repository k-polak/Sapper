package Sapper.Controler;

import Sapper.Model.Tile;

public class Board {

    private BoardConstants boardConstants;
    private Tile[][] tiles;
    private BoardStatistics boardStatistics;
    private GameWindow gameWindow;
    private boolean lost = false;


    Board(Level level){
        boardConstants = level.getBoardConstants();
        tiles = new Tile[boardConstants.getHorizontalTiles()][boardConstants.getVerticalTiles()];
        boardStatistics = new BoardStatistics(boardConstants.getAmountOfBombs());
        setupFinishingActions();
    }

    void tryToOpenTile(Tile tile){
        if (tile.isPossibleToOpen()){
            openTile(tile);
        }
    }

    private void openTile(Tile tile){
            boardStatistics.incrementTilesRevealed();
            if(boardStatistics.getTilesRevealed() == 1){
                BoardFiller.fillBoard(tiles,boardConstants.getAmountOfBombs(), tile);
                boardStatistics.timerStart();
            }
            if (tile.isBomb()) {
                gameOver();
            } else {
                tile.tryToOpen();
                if (tile.getValue() == 0) {
                    openNearbyTiles(tile);
                }
            }
    }

    void openNearbyTiles(Tile tile){
        doForNearbyTiles(tile, NearbyAction.OPEN);
    }

    void pressTile(Tile tile){
        tile.press();
    }

    void pressNearbyTiles(Tile tile){
        doForNearbyTiles(tile, NearbyAction.PRESS);
    }

    void releaseTile(Tile tile){
        tile.release();
    }

    void releaseNearbyTiles(Tile tile){
        doForNearbyTiles(tile, NearbyAction.RELEASE);
    }

    private void doForNearbyTiles(Tile tile, NearbyAction nearbyAction ){
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        int row_limit = boardConstants.getHorizontalTiles()-1;
        int column_limit = boardConstants.getVerticalTiles()-1;

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                if (i != x || j != y) {
                    switch(nearbyAction) {
                        case RELEASE:
                            releaseTile(tiles[i][j]);
                            break;
                        case PRESS:
                            pressTile(tiles[i][j]);
                            break;
                        case OPEN:
                            tryToOpenTile(tiles[i][j]);
                            break;
                    }
                }
            }
        }
    }

    void tryToRightClickOnTile(Tile tile){
        if(tile.isPossibleRightClickAction()) rightClickOnTile(tile);
    }

    private void rightClickOnTile(Tile tile){
        if(!tile.isFlagged() && !tile.isQuestionMark()){
            /*Do not change call order of functions calls
            below otherwise OpenTilesWithoutBomb will not
            run after placing last flag*/
            tile.placeFlag();
            boardStatistics.decrementMarksLeft();
        }else if(tile.isFlagged()){
            boardStatistics.incrementMarksLeft();
            tile.placeQuestionMark();
        }else{
            tile.removeQuestionMark();
        }
    }

    int countMarksAround(Tile tile){
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        int row_limit = boardConstants.getHorizontalTiles()-1;
        int counter = 0;
        int column_limit = boardConstants.getVerticalTiles()-1;
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                if (i != x || j != y) {
                    if (tiles[i][j].isFlagged()) counter++;
                }
            }
        }
        return counter;
    }
    public void addTile(Tile tile){
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        MouseActionHandler actionHandler = new MouseActionHandler(this);
        actionHandler.setMouseActions(tile);
        tiles[x][y] = tile;
    }

    void clearBoard(){
        resetTiles();
        resetBoardStatistics();
        lost = false;
    }

    private void resetBoardStatistics(){
        boardStatistics.stopTimer();
        boardStatistics.setTilesRevealed(0);
        boardStatistics.marksLeftProperty().set(boardConstants.getAmountOfBombs());
        boardStatistics.resetSecondsElapsed();
    }

    private void resetTiles(){
        for(int i = 0; i < boardConstants.getHorizontalTiles(); i++){
            for(int j = 0; j < boardConstants.getVerticalTiles(); j++){
                tiles[i][j].reset();
            }
        }
    }

    private void openEveryBomb() {
        for (int i = 0; i < boardConstants.getHorizontalTiles(); i++) {
            for (int j = 0; j < boardConstants.getVerticalTiles(); j++) {
                if (tiles[i][j].isBomb()) tiles[i][j].openBomb();
            }
        }
    }

    private void setupFinishingActions(){
        boardStatistics.tilesRevealedProperty().addListener((observableValue, oldValue, newValue) -> {
            int notRevealedTiles = boardConstants.getVerticalTiles()*boardConstants.getHorizontalTiles() - newValue.intValue();
            if( notRevealedTiles == boardConstants.getAmountOfBombs() && !lost ){
                markRemainingBombs();
                gameWon();
            }
        } );
    }

    private void markRemainingBombs(){
        for(int i = 0; i<boardConstants.getHorizontalTiles();i++){
            for(int j = 0; j <  boardConstants.getVerticalTiles();j++){
                if(tiles[i][j].isBomb() && !tiles[i][j].isFlagged())tryToRightClickOnTile(tiles[i][j]);
            }
        }
    }

    private void blockOpeningTiles(){
        for(int i = 0; i<boardConstants.getHorizontalTiles();i++){
            for(int j = 0; j <  boardConstants.getVerticalTiles();j++){
                tiles[i][j].setRevealed(true);
            }
        }
    }

    private void gameOver(){
        lost = true;
        openEveryBomb();
        blockOpeningTiles();
        boardStatistics.stopTimer();
        gameWindow.lost();
    }

    private void gameWon(){
        gameWindow.win();
        boardStatistics.getSecondsElapsed();
        boardStatistics.stopTimer();
    }

    public BoardStatistics getBoardStatistics() {
        return boardStatistics;
    }

    void setGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

}
