/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.persistence;

import es.chatserver.controllers.persistence.exceptions.IllegalOrphanException;
import es.chatserver.controllers.persistence.exceptions.NonexistentEntityException;
import es.chatserver.model.Client;
import es.chatserver.model.ClientConver;
import es.chatserver.model.ClientConverPK;
import es.chatserver.model.Conver;
import es.chatserver.model.Message;
import java.util.List;
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
    private final ClientJpaController clientJpa;
    private final ClientConverJpaController clientConverJpa;
    private final MessageJpaController msgJpa;
    
    public static PersistenceController getInstance()
    {
        
               if (instance == null){
        instanciationLock.lock();
        
        try
        {
            if (instance == null)//Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
            {
                instance = new PersistenceController();
            }
        }
        finally
        {
            instanciationLock.unlock();
        }
               }
                    return instance;

    }
    
    //Constructor privado 
    private PersistenceController()
    {
        //Incializas factoria y jpas controllerr
        emf = Persistence.createEntityManagerFactory("persistence");
        converJpa = new ConverJpaController(emf);
        clientJpa = new ClientJpaController(emf);
        clientConverJpa = new ClientConverJpaController(emf);
        msgJpa = new MessageJpaController(emf);
        
    }
    
    
    // ----- PERSISTs ----- //
    
    
    public boolean persist(Client user)
    {
        
         
        try 
        {
            clientJpa.create(user);
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
    
    // ----- FIN PERSISTs ----- //
    
    
    
    // ----- EDITs ----- //
    
    public boolean edit(Client client)
    {
         try 
        {
            clientJpa.edit(client);
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
    
    public boolean edit(ClientConver clientConver)
    {
         try 
        {
            clientConverJpa.edit(clientConver);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean edit(Message message)
    {
         try 
        {
            msgJpa.edit(message);
            return true;
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        
    // ----- FIN EDITs ----- //
    
    
    
    // ----- FINDs ----- //
    
        // ----- find by id ----- //
    
    public Client findClient(int id)
    { 
        return clientJpa.findClient(id);
    }
    
    public Conver findConver(int id)
    {
        return converJpa.findConver(id);
    }
    
    public ClientConver findClientConver(ClientConverPK id)
    {
        return clientConverJpa.findClientConver(id);
    }
    
    public Message findMessage(int id)
    {
        return msgJpa.findMessage(id);
    }
    
        // ----- find all entities ----- //
    
    public List<Client> findClientEntities()
    {
        return clientJpa.findClientEntities();
    }
    
    public List<Conver> findConverEntities()
    {
        return converJpa.findConverEntities();
    }
    
    public List<ClientConver> findClientConverEntities()
    {
        return clientConverJpa.findClientConverEntities();
    }
    
    public List<Message> findMessageEntities()
    {
        return msgJpa.findMessageEntities();
    }
    
        // ----- find entities (maxResult - minResult) ----- //
    
    public List<Client> findClientEntities(int maxResult, int minResult)
    {
        return clientJpa.findClientEntities(maxResult, minResult);
    }
    
    public List<Conver> findConverEntities(int maxResult, int minResult)
    {
        return converJpa.findConverEntities(maxResult, minResult);
    }
    
    public List<ClientConver> findClientConverEntities(int maxResult, int minResult)
    {
        return clientConverJpa.findClientConverEntities(maxResult, minResult);
    }
    
    public List<Message> findMessageEntities(int maxResult, int minResult)
    {
        return msgJpa.findMessageEntities(maxResult, minResult);
    }
    
    // ----- FIN FINDs ----- //
    
    
    // ----- GET COUNTs ----- //
    
    public int getClientCount()
    {
        return clientJpa.getClientCount();
    }
    
    public int getConverCount()
    {
        return converJpa.getConverCount();
    }
    
    public int getClientConverCount()
    {
        return clientConverJpa.getClientConverCount();
    }
    
    public int getMessageCount()
    {
        return msgJpa.getMessageCount();
    }
    
    // ----- FIN GET COUNTs ----- //
    
    
    // ----- DESTROYs ----- //
    
    public void destroyClient(int id) //Destroy client
    {
        try 
        {
            clientJpa.destroy(id);
        } 
        catch (NonexistentEntityException | IllegalOrphanException ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void destroyConver(int id) //Destroy conver
    {
        try 
        {
            converJpa.destroy(id);
        } 
        catch (IllegalOrphanException | NonexistentEntityException ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void destroyClientConver(ClientConverPK id) //Destroy clientConver
    {
        try 
        {
            clientConverJpa.destroy(id);
        } 
        catch (NonexistentEntityException ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void destroyMessage(int id) //Destroy message
    {
        try 
        {
            msgJpa.destroy(id);
        } 
        catch (NonexistentEntityException ex) 
        {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // ----- FIN DESTROYs ----- //
    
    
    
}
