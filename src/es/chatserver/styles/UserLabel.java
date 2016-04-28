/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.styles;
    
import es.chatserver.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;


/**
 *
 * @author adrinfer
 */
public class UserLabel extends MenuButton {
    
    
    //Atributos
    
    //Supongo que se necesitara el cliente al que representa el label para poder darle accion al label
    private final Client client; //Cliente asociado al label
    
    //Constructor parametrizado
    public UserLabel(Client client, ListView listView)
    {
        super(client.getNick());

        this.client = client; 
        
        this.getStyleClass().add("userLabel");
        this.setPadding(new Insets(0,0,0,25));
        this.minWidthProperty().bind(listView.minWidthProperty());
        this.maxWidthProperty().bind(listView.maxWidthProperty());
        this.prefWidthProperty().bind(listView.prefWidthProperty());
 
  
        this.addItem(new UserLabelItem("Modificar"));
        this.addItem(new UserLabelItem("Borrar"));
        this.addItem(new UserLabelItem("Bloquear"));
        
        this.popupSideProperty().set(Side.RIGHT);
        
        
    }
    
    //Añadir item, al label
    public void addItem(MenuItem item)
    {
        this.getItems().add(item);
    }
    
    //Añadir item de tipo UserLabelItem, al label
    private void addItem(UserLabelItem item)
    {
        item.setClient(client); //Asignarle cliente sobre el que recaen las acciones
        this.getItems().add(item);
    }
    
}
