/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.logic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author adrinfer
 */
public class Controller {
    
    
    private static Controller instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    
    
    public static Controller getInstance()
    {
        
        if(instance == null)
        {
            
            INSTANCIATION_LOCK.lock();

            try
            {
                if(instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new Controller();
                }
            }
            finally
            {
                INSTANCIATION_LOCK.unlock();
            }
            
        }
        
        return instance;

    }
    
    
}
