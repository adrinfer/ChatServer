/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.interfaces;

/**
 *
 * @author adrinfer
 */
public interface Observable {
    
    void addObserver(Observer obj);
    void removeObserver(Observer obj);
    void inform(Observer obj);
    void informAll();
    
}
