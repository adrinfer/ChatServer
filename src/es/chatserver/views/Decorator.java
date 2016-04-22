/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.views;

/**
 *
 * @author Practicas01
 */


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX Custom Decorator
 *
 * @author Bhathiya
 */
public class Decorator extends AnchorPane {

    private double xOffset = 0;
    private double yOffset = 0;
    private final Stage stage;

    public Decorator(Stage stage, Node node) {
        super();

        this.stage = stage;
        this.setPadding(new Insets(0, 0, 0, 0));
       
        // load css : 
        //this.getStylesheets().add("/openpimtests/Catra.css");

        Button btnMax = buildButton("||",(e) -> {
            stage.setMaximized(!stage.isMaximized());
        });
        AnchorPane.setRightAnchor(btnMax, 60.0);
        AnchorPane.setTopAnchor(btnMax, 13.0);
       
        Button btnClose = buildButton("X",(e) -> {
            stage.close();
        });
        
        AnchorPane.setRightAnchor(btnClose, 13.0);
        AnchorPane.setTopAnchor(btnClose, 13.0);
        
        Button btnRes = buildButton("G", (e) -> {
            
        });
        
        AnchorPane.setRightAnchor(btnRes, 13.0);
        AnchorPane.setBottomAnchor(btnRes, 13.0);
        
        //Ajustar
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);

        this.getChildren().addAll(node, btnMax,btnClose, btnRes);


        this.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        this.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    //Construir botón
    private Button buildButton(String name, EventHandler<ActionEvent> onAction) {
        
        Button btn = new Button(name);
        btn.setMinSize(32, 32);
        btn.setMaxSize(32, 32);
        
        //set a style 
        //btn.getStyleClass().add("white-soft-button");
        
        btn.setOnAction(onAction);
        return btn;
    }
}