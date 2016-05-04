/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server.requests;

import es.chatserver.logic.Controller;
import java.util.Date;

/**
 *
 * @author Practicas01
 */
public class LoginRequest {
    
    //Atributos
    private final String userNick;
    private final String userPassword;
    private final Date loginDate;
    
    
    private final Controller loginController;
    
    //Constructor
    public LoginRequest(String userNick, String userPassword, Date date)
    {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.loginDate = date;
        this.loginController = Controller.getInstance();
        
    }
    
    
    public String getUserNick()
    {
        return userNick;
    }
    
    public String getUserPassword()
    {
        return userPassword;
    }
    
    public Date getLoginDate()
    {
        return loginDate;
    }
    
}
