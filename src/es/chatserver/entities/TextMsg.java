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
    public final static int EMAIL_IN_USE_NOT_MODIFIED = 9;
    public final static int NICK_IN_USE_NOT_MODIFIED = 10;
    public final static int USER_PARTIALLY_MODIFIED = 11;
    public final static int USER_NOT_CREATED = 12;
    public final static int EMAIL_NOT_CORRECT = 13;
    
    //Crear mensaje recibiendo un string
    public TextMsg(String string, int type)
    {
        
        msgText = new Text();
        flowPane = new FlowPane();
        this.date = Utils.getDfMessage().format(new Date());
        this.string = string;
        setTypeString(type);
    }
    
    //Crear mensaje respecto a un cliente
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
        
        
        switch (type) 
        {
            case TextMsg.NEW_USER:
                this.msgText.setText("  " + this.date + " - Usuario REGISTRADO:  >Id: " + client.getId()+ "  >Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: green;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: green;");
                break;
                
            case TextMsg.USER_DELETE:
                this.msgText.setText("  " + this.date + " - Usuario ELIMINADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: #8A0100;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #8A0100;");
                break;
                
            case TextMsg.NEW_USER_ONLINE:
                this.msgText.setText("  " + this.date + " - Usuario online:  Id: " + client.getId() + " Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: orange;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: orange;");
                break;
                
            case TextMsg.USER_DISCONECTED:
                this.msgText.setText("  " + this.date + " - Usuario DESCONECTADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: gray;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: gray;");
                break;
                
            case TextMsg.USER_MODIFIED:
                this.msgText.setText("  " + this.date + " - Usuario MODIFICADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: gray;");
                break;
                
            case TextMsg.USER_LOCK:
                this.msgText.setText("  " + this.date + " - Usuario BLOQUEADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: orange;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: orange;");
                break;
                
            case TextMsg.USER_UNLOCK:
                this.msgText.setText("  " + this.date + " - Usuario DESBLOQUEADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                //flowPane.setStyle("-fx-background-color: green;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: green;");
                break;
                
            case TextMsg.NICK_IN_USE:
                this.msgText.setText("  " + this.date + " - Nick (" + client.getNick() + ") en uso.");
                //flowPane.setStyle("-fx-background-color: #F0E084;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.EMAIL_IN_USE:
                this.msgText.setText("  " + this.date + " - Email (" + client.getEmail() + ") en uso.");
                //flowPane.setStyle("-fx-background-color: #F0E084;");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.EMAIL_IN_USE_NOT_MODIFIED:
                this.msgText.setText("  " + this.date + " - Email en uso. No modificado:  Id: " + client.getId() + " Nick: " + client.getNick());
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.NICK_IN_USE_NOT_MODIFIED:
                this.msgText.setText("  " + this.date + " - Nick en uso. No modificado:  Id: " + client.getId() + " Nick: " + client.getNick());
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.USER_PARTIALLY_MODIFIED:
                this.msgText.setText("  " + this.date + " - Usuario PARCIALMENTE MODIFICADO:  Id: " + client.getId() + " Nick: " + client.getNick());
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: gray;");
                break;
            
            case TextMsg.USER_NOT_CREATED:
                this.msgText.setText("  " + this.date + " - Usuario NO CREADO");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #8A0100;");
                break;
             
            case TextMsg.EMAIL_NOT_CORRECT:
                this.msgText.setText("  " + this.date + " - Email incorrecto.");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            default:
                break;
        }
        
    }
    
    
    
    private void setTypeString(int type)
    {
        
        System.out.println("TYPE: " + type);
        switch(type)
        {
            case TextMsg.NICK_IN_USE:
                this.msgText.setText("  " + this.date + " - Nick \"" + string + "\" en uso.");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.EMAIL_IN_USE:
                this.msgText.setText("  " + this.date + " - Email \"" + string + "\" en uso.");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.EMAIL_NOT_CORRECT:
                this.msgText.setText("  " + this.date + " - Email incorrecto.");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #F0E084;");
                break;
                
            case TextMsg.USER_NOT_CREATED:
                this.msgText.setText("  " + this.date + " - Usuario NO CREADO");
                flowPane.setStyle("-fx-border-width: 0 0 0 20; -fx-border-color: #8A0100;");
                break;
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
