package Sapper.Model;

import Sapper.Controler.Level;
import Sapper.View.Style;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {
    private int value;
    private boolean isBomb;
    private boolean isRevealed;
    private boolean isQuestionMark;
    private boolean isFlagged;
    private Text digit;
    private Style style;
    private Position position;


    public Tile(Level level, Position position) {
        super(level.getStyle().getSideLength(), level.getStyle().getSideLength());
        value = -1;
        isFlagged = false;
        isQuestionMark = false;
        isRevealed = false;
        isBomb = false;
        digit = new Text("");
        digit.setVisible(false);
        digit.setMouseTransparent(true);
        style = level.getStyle();
        this.position = position;
        this.setArcHeight(5);
        this.setArcWidth(5);
    }

    public void press(){
        if(isRevealed || isFlagged)return;
        this.setFill(Color.web(Style.onPressedColor));
    }
    public void release(){
        if(isRevealed || isFlagged)return;
        this.setFill(Color.web(Style.defaultColor));
    }
    public void reset(){
        resetFields();
        resetStyle();
    }

    public void placeFlag(){
        isFlagged = true;
        this.setFill(style.getFlagImage());
    }

    public void placeQuestionMark(){
        isFlagged = false;
        isQuestionMark = true;
        this.setFill(style.getQuestionMarkImage());
    }

    public void removeQuestionMark(){
        isQuestionMark = false;
        this.setFill(Color.web("#5b87cf"));
    }

    public void tryToOpen(){
        if(isPossibleToOpen()) open();
    }

    public boolean isPossibleToOpen(){
        return !(isRevealed || isFlagged);
    }

    public boolean isPossibleRightClickAction(){
        return !isRevealed;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isQuestionMark() {
        return isQuestionMark;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    private void open(){
        isRevealed = true;
        this.setFill(Color.web(Style.revealedColor));
        if(!digit.getText().equals("0")) digit.setVisible(true);
    }

    private void resetFields(){
        value = -1;
        isBomb = false;
        isRevealed = false;
        isFlagged = false;
        isQuestionMark = false;
    }

    private void resetStyle(){
        digit.setText("");
        digit.setVisible(false);
        digit.setFont(style.getFont());
        this.setStroke(Color.web(Style.strokeColor));
        this.setFill(Color.web(Style.defaultColor));
        this.setArcHeight(5 );
        this.setArcWidth(5);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    public Text getDigit() {
        return digit;
    }

    public void openBomb(){
        isRevealed = true;
        this.setFill(style.getBombImage());
    }
}
