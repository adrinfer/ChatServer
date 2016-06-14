/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.chatserver.controllers.persistence.PersistenceController;

import es.chatserver.entities.TextMsg;
import es.chatserver.interfaces.Observable;
import es.chatserver.interfaces.Observer;
import es.chatserver.model.Client;
import es.chatserver.model.ClientConver;
import es.chatserver.model.ClientConverPK;
import es.chatserver.model.Conver;
import es.chatserver.model.Message;
import es.chatserver.server.messages.ConversDataMessage;
import es.chatserver.server.messages.adapters.ConversDataMessageTypeAdapter;
import es.chatserver.server.messages.adapters.RequestMessageTypeAdapter;
import es.chatserver.server.messages.adapters.ServerMessageTypeAdapter;
import es.chatserver.server.messages.requests.RequestMessage;
import es.chatserver.utils.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author adrinfer
 */
public class Controller implements Observable {
    
    
    private final List<Observer> observersList = new ArrayList<>();
    
    //Instancia de gson
    private final Gson gson;
    
    private final PersistenceController perController;
    
    
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

    private Controller() {
        
        this.perController = PersistenceController.getInstance();
        

//        Conver c = new Conver("Conver prueba otra");
//        Client client = new Client("cliente", "pass");
//        
//        this.addConver(c, "adrinfer");
        
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RequestMessage.class, new RequestMessageTypeAdapter());
        gsonBuilder.registerTypeAdapter(ConversDataMessage.class, new ConversDataMessageTypeAdapter());
        gsonBuilder.registerTypeAdapter(es.chatserver.server.messages.Message.class, new ServerMessageTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        
        this.gson = gsonBuilder.create();
        
    }
    
    
    public Gson getGson()
    {
        return this.gson;
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
        return perController.findClientEntities();
    }
    
    
    //Añadir nuevo usuario
    public boolean addUser(String nombre, String nick, String pass, String email)
    {
             
        boolean nickUse = validateNick(nick);
        boolean emailUse = validateEmail(email);
        
        if(nickUse && emailUse)
        {
            Client nuevoCliente = new Client(nombre, nick, pass, email);
            if(perController.persist(nuevoCliente))
            {
                inform(new TextMsg(nuevoCliente, TextMsg.NEW_USER));
                return true;
            } 
            else
            {
                inform(new TextMsg(email, TextMsg.USER_NOT_CREATED));
                return false;
            }
        }
        else
        {
            inform(new TextMsg(email, TextMsg.USER_NOT_CREATED));
            return false;
        }
         
    }
    
    
    //Fint client by ID
    public Client findClient(int id)
    {
       return perController.findClient(id);
    }
    
    //Find client by nickName
    public Client findClient(String nickName)
    {
       return perController.findClient(nickName);
    }
    
    public List<ClientConver> fintClientConverEntities()
    {
        return perController.findClientConverEntities();
    }
    
    public ClientConver findClientConver(String clientid, String converid)
    {
        int idclient = Integer.valueOf(clientid);
        int idconver = Integer.valueOf(converid);

        return perController.findClientConver(new ClientConverPK(idclient, idconver));
    }
    
    
    //Comprobar credenciales - LOGIN
    public boolean loginUser(String nick, String pass)
    {
        
        for(Client client: getUsersList())
        {
            if(client.getNick().equals(nick))
            {
                return client.getPass().equals(pass);
            }
            
        }
        
        return false;
        
    }
    
    
    public boolean checkLockStatus(String nick)
    {
        for(Client client: getUsersList())
        {
            if(client.getNick().equals(nick))
            {
                return client.getBloqueado() == true;
            }
            
        }
        
        return false;
    }
    
    
    //Modificar usuario
    public void edit(Client client, int msgNotify)
    {
         
        //msgNotify, muestra el estado de la modificacion
        
        if(perController.edit(client))
        {
            //Mostrar mensaje
            inform(new TextMsg(client, msgNotify));
        }
        
    }
    
    
    public boolean validateEmail(String email)
    {
        return validateEmail(email, 0);
    }
    
    
    
    public boolean validateNick(String nick)
    {
        return validateNick(nick, 0);
    }
    
    
    //Acción 1 equivale a modificar
    public boolean validateEmail(String email, int action)
    {
        
        String patternEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        
        Pattern pattern = Pattern.compile(patternEmail);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
        {
            //inform(new TextMsg(email, TextMsg.EMAIL_NOT_CORRECT));
            return false;
        }
        
        
//        //Get user list
//        List<Client> usersList = getUsersList();
//        
//        for(Client user: usersList)
//        {
//            if(user.getEmail().equals(email))
//            {
//                //Al modificar no queremos que nuestro propio email nos diga en uso
//                if(action == 0) 
//                {
//                    inform(new TextMsg(email, TextMsg.EMAIL_IN_USE));
//                }
//                
//                return false;
//            }
//        }
        
        if(!checkEmail(email))
        {
            //Al modificar no queremos que nuestro propio email nos diga en uso
            if(action == 0)
            {
                inform(new TextMsg(email, TextMsg.EMAIL_IN_USE));
            }
        }
        
        return true;
        
    }
    
    
    public boolean checkEmail(String email)
    {
        
        
        //Get user list
        List<Client> usersList = getUsersList();
        
        for(Client user: usersList)
        {
            if(user.getEmail().equals(email))
            {                
                return false;
            }
        }
        
        return true;
        
    }
    
    
    
    
    
    //Action 1 -> modify
    //Action 0 -> creation
    public boolean validateNick(String nick, int action)
    {
        
        if(!checkNick(nick))
        {
            if(action == 0)
            {
                inform(new TextMsg(nick, TextMsg.NICK_IN_USE));
            }
            
            return false;
        }
        
        return true;
        
    }
    
    
    //Check nick status
    public boolean checkNick(String nick)
    {
        //Obtener lista de usuarios
        List<Client> usersList = getUsersList();
        for(Client user: usersList)
        {
            if(user.getNick().equals(nick))
            {
                return false;
                
            }
        }
        
        return true;
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
    
    
    //Bloquear cliente
    public void lockClient(Client client)
    {
        client.setBloqueado(true);
        if(perController.edit(client))
        {
            inform(new TextMsg(client, TextMsg.USER_LOCK));
        }
        
    }
    
    
    //Desbloquear cliente
    public void unlockClient(Client client)
    {
        client.setBloqueado(false);
        if(perController.edit(client))
        {
            inform(new TextMsg(client, TextMsg.USER_UNLOCK));
        }
        
    }
    

    
    
    //Process the client request
    public int processRequest(RequestMessage request)
    {
        
        switch(request.getRequestType())
        {
            case RequestMessage.LOGIN:
                System.out.println("LOGIN");
                if(loginUser(request.getUserNick(), request.getUserPassword()))
                {
                    
                    if(checkLockStatus(request.getUserNick()))
                    {
                        return Status.USER_LOCK;
                    }
                    
                    return Status.LOGIN_OK;
                }
                return Status.LOGIN_BAD;
                
                
            case RequestMessage.REGISTER:
                
                //TODO intentar registro
                
                //Check if can register
                if(checkNick(request.getUserName()) && checkEmail(request.getUserEmail()))
                {
                    addUser(request.getUserName(),
                            request.getUserNick(),
                            request.getUserPassword(),
                            request.getUserEmail());
                    return Status.REGISTER_OK;
                }
                
                //Check is both are wrong
                if(!checkNick(request.getUserName()) && !checkEmail(request.getUserEmail()))
                {
                    return Status.USER_EMAIL_USED;
                }
                
                //Check if the wrong field is nick
                if(!checkNick(request.getUserName()))
                {
                    return Status.USER_NICK_USED;
                }
                
                //Check if the wrong field is email
                if(!checkEmail(request.getUserEmail()))
                {
                    return Status.EMAIL_ALREADY_USED;
                }

                
            
            case RequestMessage.LOGOUT:
                
                break;
                
                
            case RequestMessage.GET_DATA:
                
                break;
            
        }
        
        return 1;
        
    }
    
    
    
    //  CONVERSACIONES Y MENSAJES  //
    
    public boolean addConver(Conver conversacion)
    {
        
        
        return true;
    }
    
    
    //Create conversation without news clients, just the creator
    public boolean addConver(Conver conversation, String creator)
    {
        
        perController.persist(conversation);
        
        int clientId = perController.findClient(creator).getId();
        
        ClientConver clientConver = new ClientConver(clientId, conversation.getId());
        
        clientConver.setClient(perController.findClient(clientId));
        clientConver.setConver(conversation);
        clientConver.setAdmin(Boolean.TRUE);
        clientConver.setMod(Boolean.TRUE);
        
        return perController.persist(clientConver);
    }
    
    
    //Create conversation with initial clients and the creator - first in the array
    public boolean addConver(Conver conversation, Client[] integrantes)
    {
        
//        perController.persist(conversation);
//        
//        
//        
//        int clientId = perController.findClient(creator).getId();
//        
//        ClientConver clientConver = new ClientConver(clientId, conversation.getId());
//        
//        clientConver.setClient(perController.findClient(clientId));
//        clientConver.setConver(conversation);
//        clientConver.setAdmin(Boolean.TRUE);
//        clientConver.setMod(Boolean.TRUE);
        
        
        return true;
    }
    

    public boolean persist(Message msg)
    {
        return perController.persist(msg);
    }
    
    public List<Message> findMessagesFilterByConver(int converid)
    {
        return perController.findMessagesFilterByConver(converid);
        
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
  
    
}//end class
