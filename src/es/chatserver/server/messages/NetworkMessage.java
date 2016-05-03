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

public interface NetworkMessage {
    
    
//    //Tipos de mensaje
//    public final static int REGISTER = 0;
//    public final static int LOGIN = 1;
//    public final static int LOGOUT = 2;
//    public final static int MESSAGE = 3;
//    
//    
//    
//    
//    //Atributos
//    private final Client client;
//    private final String message;
//    private final int type;
//    
//    
//    //Constructor
//    public NetworkMessage(String message, Client client, int type)
//    {
//        this.client = client;
//        this.message = message;
//        this.type = type;
//    }
//    
//    
//    public int getType()
//    {
//        return type;
//    }
//    
//    public Client getClient()
//    {
//        return client;
//    }
    
    public Date getAlgo();
    
}
