package Sapper.View;

import Sapper.Controler.GameWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Menu {

    private StackPane menuRoot, overlay;
    private BorderPane borderPane;
    private GameWindow gameWindow;
    private OptionBox options;

    public Menu(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }

    public void build(){
        borderPane = new BorderPane();
        options = new OptionBox();
        buildMenu();
        buildOptions();
        buildCustomBox();
    }

    private void buildMenu(){
        VBox vbox = new VBox();
        StackPane stackPane = new StackPane();
        Button exitButton = new Button("Exit");
        Button basicButton = new Button("Basic");
        Button advancedButton = new Button("Advanced");
        Button expertButton = new Button("Expert");
        Button customButton = new Button("Custom");

        basicButton.setOnMouseClicked(e -> {
            gameWindow.handleBasicButton();
            options.hideOptions();
        });
        advancedButton.setOnMouseClicked(e -> {
            gameWindow.handleAdvancedButton();
            options.hideOptions();
        });
        expertButton.setOnMouseClicked(e ->{
            gameWindow.handleExpertButton();
            options.hideOptions();
        });
        customButton.setOnMouseClicked(e ->{
            gameWindow.handleCustomButton();
        });

        exitButton.setOnMouseClicked(e ->{
            gameWindow.handleExitButton();
        });

        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(basicButton,advancedButton,expertButton,customButton);

        stackPane.getChildren().addAll(vbox);

        borderPane.setCenter(stackPane);
        borderPane.setLeft(exitButton);
        BorderPane.setAlignment(exitButton,Pos.BOTTOM_LEFT);
        BorderPane.setMargin(exitButton,new Insets(0,50,20,20));

        menuRoot = new StackPane(borderPane);
        menuRoot.setStyle("-fx-background-color:"+ Style.backgroundColor);
    }

    private void buildOptions(){
        borderPane.setRight(options.getRoot());
        borderPane.setOnMouseClicked(e->{
            if(options.isVisibleOptions()) {
                options.rollUp();
            }
        });
        BorderPane.setMargin(options.getRoot(),new Insets(0,40,0,0));
    }

    private void buildCustomBox(){
        Rectangle blackout = new Rectangle(GameWindow.width, GameWindow.height);
        Rectangle container = new Rectangle(250,300);
        VBox vbox = new VBox();
        overlay = new StackPane();

        blackout.setFill(Color.web("#000000"));
        blackout.setOpacity(0.5);
        container.setFill(Color.web("#71AB12"));
        container.setArcHeight(25);
        container.setArcWidth(25);
        container.setStroke(Color.web("#1271AB"));
        container.setStrokeWidth(3);

        Slider widthSlider = new Slider(8,30,8);
        Slider heightSlider = new Slider(8,24,8);
        Slider mineSlider = new Slider(1,90,10);
        Label widthNumber = new Label();
        Label heightNumber = new Label();
        Label mineNumber = new Label();
        Label widthLabel = new Label("Width");
        Label heightLabel = new Label("Height");
        Label mineLabel = new Label("Mines");
        Button playCustomButton = new Button("Play");
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();

        playCustomButton.setOnMouseClicked(e ->{
            hideCustomBox();
            options.hideOptions();
            gameWindow.handlePlayCustomButton((int)widthSlider.getValue(),(int)heightSlider.getValue(),(int)mineSlider.getValue());
        });

        Font customBoxFont = Font.font("Verdana", FontWeight.BOLD, 14);
        heightLabel.setFont(customBoxFont);
        mineLabel.setFont( customBoxFont);
        widthLabel.setFont(customBoxFont);
        heightLabel.setFont(customBoxFont);
        mineLabel.setFont(customBoxFont);

        widthNumber.setFont(customBoxFont);
        heightNumber.setFont(customBoxFont);
        mineNumber.setFont(customBoxFont);

        widthSlider.setMajorTickUnit(1.0);
        heightSlider.setMajorTickUnit(1.0);
        mineSlider.setMajorTickUnit(1.0);

        widthSlider.setFocusTraversable(false);
        heightSlider.setFocusTraversable(false);
        mineSlider.setFocusTraversable(false);

        mineSlider.maxProperty().bind(widthSlider.valueProperty().multiply(heightSlider.valueProperty()).subtract(1));

        widthNumber.textProperty().bind(widthSlider.valueProperty().asString("%.0f"));
        heightNumber.textProperty().bind(heightSlider.valueProperty().asString("%.0f"));
        mineNumber.textProperty().bind(mineSlider.valueProperty().asString("%.0f"));

        widthNumber.setMinWidth(30);
        heightNumber.setMinWidth(30);
        mineNumber.setMinWidth(30);

        hbox1.getChildren().addAll(widthSlider, widthNumber);
        hbox2.getChildren().addAll(heightSlider, heightNumber);
        hbox3.getChildren().addAll(mineSlider, mineNumber);

        hbox1.setAlignment(Pos.CENTER);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.setAlignment(Pos.CENTER);

        mineLabel.setPadding(new Insets(0,10,0,0));

        hbox1.setSpacing(10);
        hbox2.setSpacing(10);
        hbox3.setSpacing(10);

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(widthLabel, hbox1, heightLabel, hbox2,mineLabel, hbox3, playCustomButton);

        blackout.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> hideCustomBox());
        container.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
        vbox.setMaxSize(250,300);
        overlay.getChildren().addAll(blackout,container,vbox);
    }


    public StackPane getRoot(){
        return menuRoot;
    }

    public void showCustomBox(){
        menuRoot.getChildren().add(overlay);
    }

    public void hideCustomBox(){
        menuRoot.getChildren().remove(overlay);
    }

    public RadioButton getSelectedRadioButton() {
        return options.getSelectedRadioButton();
    }
}


