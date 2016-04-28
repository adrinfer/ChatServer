/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.logic;

import es.chatserver.controllers.persistence.PersistenceController;
import es.chatserver.controllers.viewcontrollers.ServerGuiController;
import es.chatserver.interfaces.Observable;
import es.chatserver.interfaces.Observer;
import es.chatserver.model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author adrinfer
 */
public class Controller implements Observable {
    
    
    private List<Observer> observersList = new ArrayList<>();
    
    private final PersistenceController perController = PersistenceController.getInstance();
    
    
    private static Controller instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    
    //Crear y/o obtener instancia de la clase
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

    public Controller() {
        
        
    }
    
    
    
           
    //Obtener lista de usuarios con la posibilidad de filtro
    public List<Client> getUsersList(String filter)
    {
        List<Client> lista = perController.findClientEntities();
        List<Client> resultList = new ArrayList<>();
        if(!filter.equals(""))
        {
            
            for(Client client: lista)
            {
                if(client.getNick().contains(filter))
                {
                    resultList.add(client);
                }
                
            }  
        }
        else
        {
            return this.getUsersList();
        }
        
        
        return resultList;
    }
    
    
    
    //Obtener los usuarios totales in filtro
    public List<Client> getUsersList()
    {
        List<Client> lista = perController.findClientEntities();
        List<Client> resultList = new ArrayList<>();

        for(Client client: lista)
        {
            resultList.add(client);
        }
        
        return resultList;
    }
    
    
    //Eliminar usuario por ID
    public void deleteClient(Integer id)
    {
        perController.destroyClient(id);
    }
    
    


    @Override
    public void addObserver(Observer obj) {
        observersList.add(obj);
    }

    @Override
    public void removeObserver(Observer obj) {
        observersList.remove(obj);
    }

    @Override
    public void inform(Observer obj) {
        obj.update();
    }

    @Override
    public void informAll() {
        for(Observer obj: observersList)
        {
            obj.update();
        }
    }
    
}
