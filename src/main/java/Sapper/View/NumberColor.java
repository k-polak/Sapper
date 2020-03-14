package Sapper.View;

public enum NumberColor {
    ONE("#0000ff"),
    TWO("#009900"),
    THREE("#ff0000"),
    FOUR("#a50021"),
    FIVE("#0000ff"),
    SIX("#009900"),
    SEVEN("ff0000"),
    EIGHT("#a50021");

    private String color;

    NumberColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
