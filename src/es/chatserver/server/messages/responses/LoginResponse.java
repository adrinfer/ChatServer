/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package es.chatserver.server.messages.responses;

import es.chatserver.server.messages.NetworkMessage;


/**
 *
 * @author Practicas01
 */
public class LoginResponse implements NetworkMessage {
    
    
    private final int loginStatus;
    
    public LoginResponse()
    {
        loginStatus = 0;
    }
    
    public LoginResponse(int loginStatus)
    {
        this.loginStatus = loginStatus;
    }
    
    
    
}
