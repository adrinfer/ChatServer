/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;

import es.chatserver.server.messages.NetworkMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 *
 * @author adrinfer
 */
public class ClientThread implements Callable<Integer> {
    
    //Variable de estado para controlar la vida del hilo
    private boolean running;
    private final int uniqueID;
    private final Socket clientConexion;
    private final ObjectOutputStream objOutput;
    private final ObjectInputStream objInput;
    
    private NetworkMessage netMsg;
    
    public ClientThread(Socket clientConexion,ObjectInputStream objInputStream, ObjectOutputStream objOutputStream, int id)
    {
        this.uniqueID = id;
        this.clientConexion = clientConexion;
        this.running = true;
        
        this.objInput = objInputStream;
        this.objOutput = objOutputStream;
        

        
    }
    
    //Para clientThread
    public void interrupt() throws IOException
    {
        this.running = false;
        
        if(this.objInput != null)
        {
           this.objInput.close(); 
        }
        if(this.objOutput != null)
        {
           this.objOutput.close(); 
        }
        
        
    }

    @Override
    public Integer call() throws Exception {
        
        
        while(running)
        {
            netMsg = (NetworkMessage) objInput.readObject();
        }
        
        
        return 1;
    }
    
}
