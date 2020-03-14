package Sapper.Controler;

public class BoardConstants {

    private int verticalTiles;
    private int horizontalTiles;
    private int amountOfBombs;

    BoardConstants(int horizontalTiles, int verticalTiles, int amountOfBombs){
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.amountOfBombs = amountOfBombs;
    }

    public int getVerticalTiles() {
        return verticalTiles;
    }

    public int getHorizontalTiles() {
        return horizontalTiles;
    }

    int getAmountOfBombs() {
        return amountOfBombs;
    }
}
