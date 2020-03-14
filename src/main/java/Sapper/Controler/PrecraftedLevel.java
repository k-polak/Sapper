package Sapper.Controler;

import Sapper.View.Style;

public enum PrecraftedLevel {

    BASIC(new BoardConstants(8,8,10), new Style(37, 14)),
    ADVANCED(new BoardConstants(16,16,40), new Style(36, 14)),
    EXPERT(new BoardConstants(30,16,99), new Style(31, 14));//27

    private final Level level;

    PrecraftedLevel(BoardConstants boardConstants, Style style){
        level = new Level(boardConstants, style);
    }

    public Level getLevel() {
        return level;
    }

}
