/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.logic;

import es.chatserver.controllers.persistence.PersistenceController;
import es.chatserver.controllers.viewcontrollers.ServerGuiController;
import es.chatserver.entities.TextMsg;
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
//        List<Client> lista = perController.findClientEntities();
//        List<Client> resultList = new ArrayList<>();
//
//        for(Client client: lista)
//        {
//            resultList.add(client);
//        }
        
        return perController.findClientEntities();
    }
    
    
    //Añadir nuevo usuario
    public void addUser(String nombre, String nick, String pass, String email)
    {
        
        List<Client> usersList = getUsersList();
        
        boolean nickUse = false;
        boolean emailUse = false;
        
        for(Client client: usersList)
        {
            if(client.getNick().equals(nick))
            {
                nickUse = true;
            }
            
            if(client.getEmail().equals(email))
            {
                emailUse = true;
            }
            
            
            if(nickUse && emailUse)
            {
                inform(new TextMsg(nick, TextMsg.NICK_IN_USE));
                inform(new TextMsg(email, TextMsg.EMAIL_IN_USE));
                return;
            }
            else if(emailUse)
            {
                inform(new TextMsg(email, TextMsg.EMAIL_IN_USE));
                return;
            }
            else if(nickUse)
            {
                inform(new TextMsg(nick, TextMsg.NICK_IN_USE));
                return;
            }
            
            
        }
        
        Client nuevoCliente = new Client(nombre, nick, pass, email);
         if(perController.persist(nuevoCliente))
        {
            inform(new TextMsg(nuevoCliente, TextMsg.NEW_USER));
        }
        
    }
    
    //Modificar usuario
    public void modifyUser(Client client)
    {
        //Obtener lista de usuarios
        List<Client> usersList = getUsersList();
        
        boolean nickUse = false;
        boolean emailUse = false;
        
        for(Client c: usersList)
        {
            if(c.getNick().equals(client.getNick()))
            {
                System.out.println("DENTRO NICK");
                nickUse = true;
            }
            
            if(c.getEmail().equals(client.getEmail()))
            {
                System.out.println("DENTRO EMAIL");
                emailUse = true;
            }
            
            
            if(nickUse && emailUse)
            {
                System.out.println("ENTRO DOBLE");
                inform(new TextMsg(client.getNick(), TextMsg.NICK_IN_USE));
                inform(new TextMsg(client.getEmail(), TextMsg.EMAIL_IN_USE));
                return;
            }
            else if(emailUse)
            {
                inform(new TextMsg(client.getEmail(), TextMsg.EMAIL_IN_USE));
                return;
            }
            else if(nickUse)
            {
                inform(new TextMsg(client.getNick(), TextMsg.NICK_IN_USE));
                return;
            }
        
        }
        
        
//        if(perController.edit(client))
//        {
//            //Mostrar mensaje
//            inform(new TextMsg(client, TextMsg.USER_MODIFIED));
//        }
        
    }
    
    
    //Eliminar usuario por ID
    public void deleteClient(Integer id)
    {
        
        Client clientToDelete = perController.findClient(id);
        if(perController.destroyClient(id))
        {
            inform(new TextMsg(clientToDelete, TextMsg.USER_DELETE));
        }
    }
    
    
    public void lockClient(Client client)
    {
        client.setBloqueado(true);
        if(perController.edit(client))
        {
            inform(new TextMsg(client, TextMsg.USER_LOCK));
        }
        
    }
    
    public void unlockClient(Client client)
    {
        client.setBloqueado(false);
        if(perController.edit(client))
        {
            inform(new TextMsg(client, TextMsg.USER_UNLOCK));
        }
        
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
    public void inform(TextMsg message) {
        for(Observer obj: observersList)
        {
            obj.update(message);
        }
        
    }

    @Override
    public void informAll() {
        for(Observer obj: observersList)
        {
            obj.update();
        }
    }
    
}
