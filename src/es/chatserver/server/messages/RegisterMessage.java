/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server.messages;


import java.util.Date;

/**
 *
 * @author Practicas01
 */

public class RegisterMessage implements NetworkMessage {
    
    
    
    private final Date date;
    
    public RegisterMessage()
    {
        date = new Date();
    }
    
    public Date getDate()
    {
        return date;
    }

    @Override
    public Date getAlgo() {
        return date;
    }
}
