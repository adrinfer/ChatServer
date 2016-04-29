/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.interfaces;

import es.chatserver.entities.TextMsg;

/**
 *
 * @author adrinfer
 */
public interface Observer {
    
    void update();
    void update(TextMsg message);
    
}
