/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.styles;

import javafx.scene.control.MenuItem;

/**
 *
 * @author Practicas01
 */
public class UserLabelItem extends MenuItem{
    
    public UserLabelItem(String txt)
    {
        super(txt);
        
        this.getStyleClass().add("userLabel");
        
        
        
    }
    
}
