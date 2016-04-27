/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.styles;
    
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;


/**
 *
 * @author adrinfer
 */
public class UserLabel extends MenuButton {
    
    
    //Atributos
    
    //Supongo que se necesitara el cliente al que representa el label para poder darle accion al label
    //private Client cliente;
    
    public UserLabel(String txt, ListView listView)
    {
        super(txt);
        
        this.getStyleClass().add("userLabel");
        this.setPadding(new Insets(0,0,0,25));
        this.minWidthProperty().bind(listView.minWidthProperty());
        this.maxWidthProperty().bind(listView.maxWidthProperty());
        this.prefWidthProperty().bind(listView.prefWidthProperty());
        
        this.getItems().add(new UserLabelItem("Texto 1"));

        this.getItems().add(new UserLabelItem("Texto 2"));

        
        this.popupSideProperty().set(Side.RIGHT);
        
        
        
        
    }
    
}
