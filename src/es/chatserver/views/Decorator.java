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


import es.chatserver.resources.Images;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author adrinfer
 */
public class Decorator extends AnchorPane {

    //Atributos
    private final Stage stage;
    private double oldWidth;
    private double oldHeight;
    private double oldX;
    private double oldY;
    
    
    //Constructor
    public Decorator(Stage stage, Node node) {
        
        super();
        
        this.stage = stage;
        this.setPadding(new Insets(0, 0, 0, 0));
       
        //Ajustar nodo recibido a las esquinas
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        
                
        //Boón para maximizar la ventana
        Button btnMax = buildButton("Max",Images.getImage(Images.MAX_ICON), Images.getImage(Images.MAX_ICON_HOVER));
        btnMax.setOnAction((event) -> {
            this.stage.setMaximized(!this.stage.isMaximized()); 
        });
        
        
        //Posición botón maximizar
        AnchorPane.setRightAnchor(btnMax, 60.0);
        AnchorPane.setTopAnchor(btnMax, 6.5);
       
        
        //Botón para cerrar la ventana
        Button btnClose = buildButton("Close", Images.getImage(Images.CLOSE_ICON), Images.getImage(Images.CLOSE_ICON_HOVER));
        
        btnClose.setOnAction((event) -> {
            
            //Transición de cierre
            ScaleTransition scaleTransitionClose = ScaleTransitionBuilder.create()
                .node(this)
                .duration(Duration.seconds(0.3))
                .fromX(1)
                .fromY(1)
                .toX(0)
                .toY(0)
                .build();
            
            //Cerrar cuando termine la animación
            scaleTransitionClose.setOnFinished((e) -> {
                this.stage.close();
            
            });
            
            //Iniciar transicion de cierre
            scaleTransitionClose.play();
        });
        
        //Posición boton close
        AnchorPane.setRightAnchor(btnClose, 13.0);
        AnchorPane.setTopAnchor(btnClose, 6.5);
        
        
        //Botón para redimensionar la ventana
        Button btnRes = buildButton("Resize", Images.getImage(Images.RESIZE_ICON), Images.getImage(Images.RESIZE_ICON_HOVER));
          
        btnRes.setOnMousePressed((event) -> {
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_PRESSED)));
            this.oldX = event.getSceneX();
            this.oldY = event.getSceneY();
            this.oldWidth = stage.getWidth();
            this.oldHeight = stage.getHeight();

        });
       
        
        btnRes.setOnMouseReleased((event) -> {
              btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON)));               
        });
        
        btnRes.setOnMouseDragged((event) -> {
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_PRESSED)));
            double newWidth = this.oldWidth + (event.getSceneX() - this.oldX);
            double newHeight = this.oldHeight + (event.getSceneY() - this.oldY);
            
            if(newWidth >= 600)
            {
                stage.setWidth(newWidth);
            }
            
            if(newHeight >= 430)
            {
                stage.setHeight(newHeight);
            }
                                    
        });
        
        btnRes.setOnMouseEntered((event) -> {
            btnRes.setCursor(Cursor.SE_RESIZE);
            btnRes.setGraphic(new ImageView(Images.getImage(Images.RESIZE_ICON_HOVER)));
        });
        
        //Posición botón de redimensionar
        AnchorPane.setRightAnchor(btnRes, 1.5);
        AnchorPane.setBottomAnchor(btnRes, 1.5);
        

        this.getChildren().addAll(node, btnMax, btnClose, btnRes);
 
    }

    
    //Construir botón con una imagen y imagen de hover (pasar por encima)
    private Button buildButton(String name,  Image image, Image imageHover) {
        
        Button btn = new Button(name, new ImageView(image));
        btn.setOnMouseEntered((event) -> {
            btn.setCursor(Cursor.HAND);
            btn.setGraphic(new ImageView(imageHover));
            
        });
        
        btn.setOnMouseExited((event) -> {
            
            btn.setGraphic(new ImageView(image));
        });
        
        btn.getStyleClass().clear();
        btn.setMinSize(32, 32);
        btn.setMaxSize(32, 32);
        
        return btn;
    } 
    
}