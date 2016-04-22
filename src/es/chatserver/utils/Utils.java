/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.utils;

import java.text.SimpleDateFormat;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author adrinfer
 */
public class Utils {
    
    
    //Movimiento y posición del stage
    static class Delta { 
         double x, y;   
    }
     
    static final Delta dragDelta = new Delta();
    
    
    
    //Obtener formato de fecha para los mensajes
    public SimpleDateFormat getDfMessage()
    {
        return new SimpleDateFormat("HH:mm:ss");
    }
    
    
    public static void makeDraggable(Stage stage, Node node)
    {
        
        node.setOnMouseEntered((event) -> {
            node.setCursor(Cursor.MOVE);
        });
        
        node.setOnMousePressed((event) -> {
            node.setCursor(Cursor.CLOSED_HAND);
            dragDelta.x = stage.getX() - event.getScreenX();
            dragDelta.y = stage.getY() - event.getScreenY();
        });
        
        node.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() + dragDelta.x);
            stage.setY(event.getScreenY() + dragDelta.y);
        });
        
        node.setOnMouseReleased((event) -> {
            node.setCursor(Cursor.MOVE);
            stage.setX(event.getScreenX() + dragDelta.x);
            stage.setY(event.getScreenY() + dragDelta.y);
        });
        
        
    }
    
}
