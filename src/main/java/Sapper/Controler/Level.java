package Sapper.Controler;

import Sapper.View.Style;

public class Level {
    private final Style style;
    BoardConstants boardConstants;

    Level(BoardConstants boardConstants, Style style){
        this.boardConstants = boardConstants;
        this.style = style;
    }

    Level(int horizontalTiles, int verticalTiles, int amountOfBombs){
        boardConstants = new BoardConstants(horizontalTiles,verticalTiles,amountOfBombs);
        this.style = new Style(computeSideLength(horizontalTiles*verticalTiles),14);
    }

    private int computeSideLength(int numberOfTiles){
        double a1 = -1.0/192;
        double b1 = 37.3;
        double a2 = -5.0/224;
        double b2 = 41.7;
        if(numberOfTiles<256){
            return (int)(a1*numberOfTiles+b1);
        }else{
            return (int)(a2*numberOfTiles+b2);
        }
    }

    public Style getStyle() {
        return style;
    }

    public BoardConstants getBoardConstants() {
        return boardConstants;
    }
}
