/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.model.Client;
import es.chatserver.model.ClientConver;
import es.chatserver.model.ClientConverPK;
import es.chatserver.model.Conver;
import es.chatserver.model.Message;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author adrinfer
 */
public class PersistenceController {
    
    private static PersistenceController instance = null;
    private final  static  Lock instanciationLock = new ReentrantLock();
    private final EntityManagerFactory emf;
    
    private final ConverJpaController converJpa;
    private final ClientJpaController userJpa;
    private final ClientConverJpaController clientConverJpa;
    private final MessageJpaController msgJpa;
    
    public static PersistenceController getInstance()
    {
        
        instanciationLock.lock();
        
        try
        {
            if (instance == null)
            {
                instance = new PersistenceController();
            }
            return instance;
        }
        finally
        {
            instanciationLock.unlock();
        }
    }
    
    //Constructor privado 
    private PersistenceController()
    {
        //Incializas factoria y jpas controllerr
        emf = Persistence.createEntityManagerFactory("persistence");
        converJpa = new ConverJpaController(emf);
        userJpa = new ClientJpaController(emf);
        clientConverJpa = new ClientConverJpaController(emf);
        msgJpa = new MessageJpaController(emf);
        
    }
    
    
    public boolean persist(Client user)
    {
        
         
        try 
        {
            userJpa.create(user);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    
    public boolean persist(Conver conver)
    {
         
        try 
        {
            converJpa.create(conver);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public boolean persist(ClientConver clientConver)
    {
         
        try 
        {
            clientConverJpa.create(clientConver);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    
    public boolean persist(Message msg)
    {
         
        try 
        {
            msgJpa.create(msg);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    
    public boolean edit(Conver conver)
    {
         try 
        {
            converJpa.edit(conver);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public Client findClient(Integer id)
    {
        
        return userJpa.findClient(id);
        
    }
    
    public Conver findConver(Integer id)
    {
        
        return converJpa.findConver(id);
        
    }
    
    public ClientConver findClientConver(ClientConverPK id)
    {
        
        return clientConverJpa.findClientConver(id);
        
    }
    
//    public boolean persist(Message message)
//    {
//        
//    }
    
}
