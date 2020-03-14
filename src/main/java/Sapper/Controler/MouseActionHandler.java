package Sapper.Controler;

import Sapper.Model.Tile;
import javafx.scene.input.MouseButton;

class MouseActionHandler {
    private Board board;

    MouseActionHandler(Board board){
        this.board = board;
    }

    void setMouseActions(Tile tile){
        tile.setOnDragDetected(e -> {
            tile.startFullDrag();
        });
        tile.setOnMousePressed(e -> {
            onPressAction(tile, e.getButton());
        });
        tile.setOnMouseReleased(e -> {
            restoreDefaultOnRelease(tile, e.getButton());
            if (e.isDragDetect()) { //true <=> drag didnt happen
                onReleaseAction(tile,e.getButton());
            }
        });
        tile.setOnMouseDragReleased(e -> {
            onReleaseAction(tile, e.getButton());
        });
        tile.setOnMouseDragEntered(e -> {
            if (e.isPrimaryButtonDown()) {
                onPressAction(tile, MouseButton.PRIMARY);
            } else if (e.isMiddleButtonDown()) {
                onPressAction(tile, MouseButton.MIDDLE);
            }
        });
        tile.setOnMouseDragExited(e -> {
            if (e.isPrimaryButtonDown()) {
                restoreDefaultOnRelease(tile, MouseButton.PRIMARY);
            } else if (e.isMiddleButtonDown()) {
                restoreDefaultOnRelease(tile, MouseButton.MIDDLE);
            }
        });
    }

    private void onPressAction(Tile tile, MouseButton mouseButton){
        if(mouseButton == MouseButton.PRIMARY){
            board.pressTile(tile);
        }else if(mouseButton == MouseButton.MIDDLE ){
            board.pressTile(tile);
            board.pressNearbyTiles(tile);
        }
    }

    private void onReleaseAction(Tile tile,  MouseButton mouseButton){
        if(mouseButton == MouseButton.PRIMARY){
            board.tryToOpenTile(tile);
        }else if(mouseButton == MouseButton.SECONDARY) {
            board.tryToRightClickOnTile(tile);
        }else if(mouseButton == MouseButton.MIDDLE ){
            onReleaseMiddleButton(tile);
        }
    }

    private void onReleaseMiddleButton(Tile tile){
        if(canOpenNearbyTiles(tile)){
            board.openNearbyTiles(tile);
        }else{
            restoreDefaultOnRelease(tile,MouseButton.MIDDLE);
        }
    }

    private void restoreDefaultOnRelease(Tile tile, MouseButton mouseButton){
        if(mouseButton == MouseButton.PRIMARY || mouseButton == MouseButton.SECONDARY){
            board.releaseTile(tile);
        }else if(mouseButton == MouseButton.MIDDLE ){
            board.releaseTile(tile);
            board.releaseNearbyTiles(tile);
        }
    }

    private boolean canOpenNearbyTiles(Tile tile){
        return tile.isRevealed() && tile.getValue() == board.countMarksAround(tile);
    }

}
