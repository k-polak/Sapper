package Sapper.View;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class OptionBox {

    private final ToggleGroup group = new ToggleGroup();
    private boolean visibleOptions;

    private StackPane root;
    private StackPane container;

    private RotateTransition rotateButton;
    private TranslateTransition optionsTranslation;

    OptionBox(){
        build();
    }

    public void build(){
        root = new StackPane();
         visibleOptions = false;
        Button optionButton = new Button();
        Label optionLabel = new Label("On first click");
        container = new StackPane();
        Rectangle curtain = new Rectangle();
        VBox vbox = new VBox();

        optionButton.setId("optionButton");
        optionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        optionLabel.setPadding(new Insets(20,0,7,10));

        rotateButton = new RotateTransition(Duration.millis(1000), optionButton);
        rotateButton.setInterpolator(Interpolator.LINEAR);
        optionsTranslation = new TranslateTransition(Duration.millis(1000),container);
        optionsTranslation.setFromY(-50);
        optionsTranslation.setToY(120);

        curtain.setFill(Color.web(Style.backgroundColor));
        curtain.setWidth(150);
        curtain.setHeight(100);

        RadioButton rb1 = new RadioButton("Random");
        rb1.setToggleGroup(group);
        rb1.setFocusTraversable(false);
        rb1.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        rb1.setPadding(new Insets(0,0,0,10));

        RadioButton rb2 = new RadioButton("No bomb");
        rb2.setToggleGroup(group);
        rb2.setFocusTraversable(false);
        rb2.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        rb2.setPadding(new Insets(0,0,0,10));

        RadioButton rb3 = new RadioButton("Zero field");
        rb3.setToggleGroup(group);
        rb3.setSelected(true);
        rb3.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        rb3.setFocusTraversable(false);
        rb3.setPadding(new Insets(0,0,20,10));

        vbox.getChildren().addAll(optionLabel, rb1, rb2, rb3);
        vbox.setSpacing(10);
        vbox.setStyle(
                "-fx-background-color:"+Style.optionsBoxColor+";" +
                "-fx-border-radius: 25 25 25 25;" +
                "-fx-background-radius: 25 25 25 25;");

        vbox.setAlignment(Pos.CENTER_LEFT);
        container.getChildren().addAll(vbox);
        container.setMaxSize(150,100);
        container.setTranslateY(-50);

        StackPane.setMargin(optionButton,new Insets(20,-10,0,0));
        root.setMinWidth(150);

        root.setStyle("-fx-background-color:"+ Style.backgroundColor);
        root.getChildren().addAll(container,curtain, optionButton);
        root.setAlignment(Pos.TOP_LEFT);

        optionButton.setOnMouseClicked(e->{
            if(!visibleOptions) {
                rollDown();
            }else{
                rollUp();
            }
        });
        container.setOnMouseClicked(e->{
            e.consume();
        });
    }

    void rollUp(){
        visibleOptions = false;
        rotateButton.stop();
        rotateButton.setByAngle(360);
        rotateButton.play();
        optionsTranslation.setRate(-1);
        optionsTranslation.play();
    }

    private void rollDown(){
        visibleOptions = true;
        rotateButton.stop();
        rotateButton.setByAngle(-360);
        rotateButton.play();
        optionsTranslation.setRate(1);
        optionsTranslation.play();
    }
    void hideOptions(){
        optionsTranslation.stop();
        container.setTranslateY(-50);
        visibleOptions = false;
    }

    boolean isVisibleOptions() {
        return visibleOptions;
    }

    StackPane getRoot(){
        return root;
    }

    RadioButton getSelectedRadioButton(){
        return (RadioButton)group.getSelectedToggle();
    }
}
