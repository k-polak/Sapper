package Sapper.Controler;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class BoardStatistics {
    private IntegerProperty tilesRevealed;
    private IntegerProperty marksLeft;
    private IntegerProperty secondsElapsed;
    private Timeline timer;


    BoardStatistics(int amountOfBombs){
        tilesRevealed =  new SimpleIntegerProperty(0);
        marksLeft = new SimpleIntegerProperty(amountOfBombs);
        secondsElapsed = new SimpleIntegerProperty(0);

        timer = new Timeline(new KeyFrame(Duration.seconds(1),e ->{
            incrementSecondsElapsed();
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    int getMarksLeft() {
        return marksLeft.get();
    }

    public IntegerProperty marksLeftProperty() {
        return marksLeft;
    }

    void incrementMarksLeft(){
        marksLeft.setValue(getMarksLeft()+1);
    }

    void decrementMarksLeft(){
        marksLeft.setValue(getMarksLeft()-1);
    }

    void incrementTilesRevealed() {
        tilesRevealed.set(tilesRevealed.getValue()+1);
    }
    void stopTimer(){
        timer.stop();
    }

    void timerStart(){
        timer.playFromStart();
    }

    int getSecondsElapsed() {
        return secondsElapsed.get();
    }

    public IntegerProperty secondsElapsedProperty() {
        return secondsElapsed;
    }

    void resetSecondsElapsed(){
        secondsElapsed.set(0);
    }

    private void incrementSecondsElapsed(){
        if(secondsElapsed.get() + 1 > 999){
            secondsElapsed.set(999);
        }else{
            secondsElapsed.set(secondsElapsed.get() + 1);
        }
    }

    int getTilesRevealed() {
        return tilesRevealed.get();
    }

    IntegerProperty tilesRevealedProperty() {
        return tilesRevealed;
    }

    void setTilesRevealed(int tilesRevealed) {
        this.tilesRevealed.set(tilesRevealed);
    }
}
