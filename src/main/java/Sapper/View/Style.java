package Sapper.View;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Style {
    public final static String defaultColor = "#5b87cf";
    public final static String onHoverColor = "#6463E0";
    public final static String onPressedColor = "#396fc6";
    public final static String strokeColor = "#3d3d5c";
    public final static String revealedColor = "#c2c2d6";
    final static String optionsBoxColor = "#1bb1a2";
    final static String backgroundColor = "#117168";

    private double sideLength;
    private  Font font;
    private ImagePattern flagImage, questionMarkImage, bombImage;

    public Style(double sideLength, int fontSize){
        this.sideLength = sideLength;
        this.font = Font.font("Verdana", FontWeight.BOLD, fontSize);
        flagImage = new ImagePattern(new Image(getClass().getResource("/flag.png").toExternalForm()));
        questionMarkImage = new ImagePattern(new Image(getClass().getResource("/questionMark.png").toExternalForm()));
        bombImage = new ImagePattern(new Image(getClass().getResource("/bomb.png").toExternalForm()));
    }

    public double getSideLength() {
        return sideLength;
    }

    public Font getFont() {
        return font;
    }

    public ImagePattern getFlagImage() {
        return flagImage;
    }

    public ImagePattern getQuestionMarkImage() {
        return questionMarkImage;
    }

    public ImagePattern getBombImage() {
        return bombImage;
    }


    public static String getNumberColor(int number){
        switch(number){
            case 1:
                return NumberColor.ONE.getColor();
            case 2:
                return NumberColor.TWO.getColor();
            case 3:
                return NumberColor.THREE.getColor();
            case 4:
                return NumberColor.FOUR.getColor();
            case 5:
                return NumberColor.FIVE.getColor();
            case 6:
                return NumberColor.SIX.getColor();
            case 7:
                return NumberColor.SEVEN.getColor();
            case 8:
                return NumberColor.EIGHT.getColor();
            default:
                return "#000000";
        }
    }
}
