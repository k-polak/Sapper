package Sapper.Controler;


import Sapper.Model.Tile;
import Sapper.View.Style;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BoardFiller {
    private static BombAppearance bombAppearance;

    static void fillBoard(Tile tiles[][], int amountOfBombs, Tile clicked){
        generateBombs(tiles,amountOfBombs, clicked);
        fillBoardWithNumbers(tiles);
    }

    private static void generateBombs(Tile tiles[][], int amountOfBombs, Tile clicked){
        List<Integer> ids = new ArrayList();
        for(int i = 1; i <= tiles.length * tiles[0].length; i++ ){
            ids.add(i);
        }

        switch(bombAppearance){
            case IMPOSSIBLE_AT_FIRST:
                ids.remove(Integer.valueOf(clicked.getId()));
                break;
            case ZERO_FIELD_AT_FIRST:
                makeZeroFieldAtClick(tiles,clicked,ids);
                break;
            case POSSIBLE_AT_FIRST:
                break;
        }
        Collections.shuffle(ids);
        List<Integer> bombs = ids.subList(0,amountOfBombs);

        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                if(bombs.contains(Integer.valueOf(tiles[i][j].getId()))) tiles[i][j].setBomb(true);
            }
        }
    }

    private static void fillBoardWithNumbers(Tile tiles[][]){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j <tiles[0].length; j++){
                if(!tiles[i][j].isBomb() && tiles[i][j].getValue() == -1){
                    tiles[i][j].setValue(countBombsAround(tiles, tiles[i][j]));
                    tiles[i][j].getDigit().setText(String.valueOf(tiles[i][j].getValue()));
                    tiles[i][j].getDigit().setFill(Color.web(Style.getNumberColor(tiles[i][j].getValue())));
                }
            }
        }
    }

    private static int countBombsAround(Tile tiles[][], Tile tile){
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        int row_limit = tiles.length-1;
        int counter = 0;
        int column_limit = tiles[0].length-1;
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                if (i != x || j != y) {
                    if (tiles[i][j].isBomb()) counter++;
                }
            }
        }
        return counter;
    }

    static void setBombAppearance(BombAppearance bombAppearance){
        BoardFiller.bombAppearance = bombAppearance;
    }

    private static void makeZeroFieldAtClick(Tile tiles[][], Tile clicked, List bombPossibilities){
        int x = clicked.getPosition().getX();
        int y = clicked.getPosition().getY();
        int row_limit = tiles.length-1;
        int column_limit = tiles[0].length-1;
        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                bombPossibilities.remove(Integer.valueOf(tiles[i][j].getId()));
            }
        }
    }
}
