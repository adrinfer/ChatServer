/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.entities;

import es.chatserver.controllers.viewcontrollers.ServerGuiController;
import es.chatserver.model.Client;
import es.chatserver.utils.Utils;
import java.util.Date;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 *
 * @author Practicas01
 */
public class TextMsg {
    
    private final FlowPane flowPane;
    private final Text msgText;
    private String string;
    private Client client;
    private final String date;
    
    //Variables estáticas para los tipos de mensajes
    public final static int NEW_USER = 0;
    public final static int USER_DELETE = 1;
    public final static int NEW_USER_ONLINE = 2;
    public final static int USER_DISCONECTED = 3;
    public final static int USER_MODIFIED = 4;
    public final static int USER_LOCK = 5;
    public final static int USER_UNLOCK = 6;
    public final static int NICK_IN_USE = 7;
    public final static int EMAIL_IN_USE = 8;
    
    public TextMsg(String string, int type)
    {
        
        msgText = new Text();
        flowPane = new FlowPane();
        this.date = Utils.getDfMessage().format(new Date());
        this.string = string;
        setTypeString(type);
    }
    
    public TextMsg(Client client, int type)
    {
        
        msgText = new Text();
        flowPane = new FlowPane();
        this.date = Utils.getDfMessage().format(new Date());
        this.client = client;
        setTypeClient(type);
    }
    
    
    //Obtener mensaje según el tipo
    private void setTypeClient(int type)
    {
        
        
        if(type == TextMsg.NEW_USER)
        {
            this.msgText.setText("  " + this.date + " - Usuario REGISTRADO:  >Id: " + client.getId()+ "  >Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: green;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: green;");
            
        }
        else if(type == TextMsg.USER_DELETE)
        {
            this.msgText.setText("  " + this.date + " - Usuario ELIMINADO:  Id: " + client.getId() + " Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: #8A0100;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #8A0100;");
        }
        else if(type == TextMsg.NEW_USER_ONLINE)
        {
            this.msgText.setText("  " + this.date + " - Usuario online:  Id: " + client.getId() + " Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: orange;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: orange;");
        }
        else if(type == TextMsg.USER_DISCONECTED)
        {
            this.msgText.setText("  " + this.date + " - Usuario DESCONECTADO:  Id: " + client.getId() + " Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: gray;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: gray;");
        }
        else if(type == TextMsg.USER_MODIFIED)
        {
            this.msgText.setText("  " + this.date + " - Usuario MODIFICADO:  Id: " + client.getId() + " Nick: " + client.getNick());
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: gray;");
        }
        else if(type == TextMsg.USER_LOCK)
        {
            this.msgText.setText("  " + this.date + " - Usuario BLOQUEADO:  Id: " + client.getId() + " Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: orange;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: orange;");
        }
        else if(type == TextMsg.USER_UNLOCK)
        {
            this.msgText.setText("  " + this.date + " - Usuario DESBLOQUEADO:  Id: " + client.getId() + " Nick: " + client.getNick());
            //flowPane.setStyle("-fx-background-color: green;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: green;");
        }
        else if(type == TextMsg.NICK_IN_USE)
        {
            this.msgText.setText("  " + this.date + " - Nick (" + client.getNick() + ") en uso.");
            //flowPane.setStyle("-fx-background-color: #F0E084;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
        }
        else if(type == TextMsg.EMAIL_IN_USE)
        {
            this.msgText.setText("  " + this.date + " - Email (" + client.getEmail() + ") en uso.");
            //flowPane.setStyle("-fx-background-color: #F0E084;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
        }
        
    }
    
    
    private void setTypeString(int type)
    {
        if(type == TextMsg.NICK_IN_USE)
        {
            this.msgText.setText("  " + this.date + " - Nick \"" + string + "\" en uso.");
            //flowPane.setStyle("-fx-background-color: #F0E084;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
        }
        else if(type == TextMsg.EMAIL_IN_USE)
        {
            this.msgText.setText("  " + this.date + " - Email \"" + string + "\" en uso.");
            //flowPane.setStyle("-fx-background-color: #F0E084;");
            flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
        }
        
        
        
    }
    
    public FlowPane getMessage()
    {
        
        //Se el resta 85 para que no se superponga el scrollBar de la derecha
        msgText.wrappingWidthProperty().bind(ServerGuiController.getInstance().getVboxMessageList().widthProperty().subtract(85));
        
        flowPane.getChildren().add(msgText); 
        return flowPane;
    }

    
}
