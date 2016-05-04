/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server.messages;

import java.util.Date;

/**
 *
 * @author adrinfer
 */
public class NetworkMessage {
    
    //Tipos de mensaje
    public final static int REGISTER_REQUEST = 0;
    public final static int LOGIN_REQUEST = 1;
    public final static int LOGOUT_REQUEST = 2;
    public final static int MESSAGE = 3;
    public final static int ERROR_MESSAGE = 4;
    
      
    //Tipo de petición
    private final int typeRequest;
    
    //Datos del usuario
    private String userName;
    private String userNick;
    private String userPassword;
    private String userEmail;
    //private String userLockStatus;
        
    //Datos de mensaje
    private String msgText;
    private int converId;
    
    //Date
    private Date date;
    
    
    public NetworkMessage(int type)
    {
        this.typeRequest = type;
    }
    
    
    //Obtener tipo de petición
    public int getType()
    {
        return typeRequest;
    }
    
    
    //Establecer datos del usuario
    public void setUserData(String name, String nick, String password, String email)
    {
        this.userName = name;
        this.userNick = nick;
        this.userPassword = password;
        this.userEmail = email;
    }
    
    
    //Establecer datos de mensaje
    public void setMessageData(String msgText, int converId)
    {
        this.msgText = msgText;
        this.converId = converId;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public String getUserNick()
    {
        return userNick;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }
    
    public String getUserEmail()
    {
        return userEmail;
    }
    
    public Date getDate()
    {
        return date;
    }
    
 
    
}
    


