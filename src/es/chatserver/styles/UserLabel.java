/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.styles;
    
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author adrinfer
 */
public class UserLabel extends Label {
    
    
    //Atributos
    
    //Supongo que se necesitara el cliente al que representa el label para poder darle accion al label
    //private Client cliente;
    
    public UserLabel(String txt, Pane parent)
    {
        super(txt);
        
        this.getStyleClass().add("userLabel");
        this.setPadding(new Insets(0,0,0,25));
        this.minWidthProperty().bind(parent.minWidthProperty());
        this.maxWidthProperty().bind(parent.maxWidthProperty());
        this.prefWidthProperty().bind(parent.prefWidthProperty());
        
        
        
        
    }
    
}
