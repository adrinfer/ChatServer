/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;


import java.util.ArrayList;

/**
 *
 * @author Practicas01
 */
public class Server extends ServerService {

   
    
    
    
    //Constructor
    public Server(int port) {
        super(port); //Inicializa el serverSocket
        
    }

    
    
//    //Obtener array de clientes
//    public ArrayList<ClientThread> getClientList()
//    {
//        return clientList;
//    }
//    
//    
//    //Obtener tamaño del array de clientes
//    public int getOnlineClientsCount()
//    {
//        return clientList.size();
//    }
}
