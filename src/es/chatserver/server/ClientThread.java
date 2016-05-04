/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;

import com.google.gson.Gson;
import es.chatserver.server.messages.NetworkMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    
    
    private NetworkMessage request;
    private final Gson gson;
    
    public ClientThread(Socket clientConexion, DataInputStream dataInputStream, DataOutputStream dataOutputStream, int id)
    {
        this.uniqueID = id;
        this.clientConexion = clientConexion;
        this.running = true;
        
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        
        this.gson = new Gson();

        
    }
    
    //Para clientThread
    public void interrupt() throws IOException
    {
        this.running = false;
        
        if(this.dataInputStream != null)
        {
           this.dataInputStream.close(); 
        }
        if(this.dataOutputStream != null)
        {
           this.dataOutputStream.close(); 
        }
        
        
    }

    @Override
    public Integer call() throws Exception {
        
        
        while(running)
        {
            String json = dataInputStream.readUTF();
            request = gson.fromJson(json, NetworkMessage.class);
            System.out.println(request.getUserNick());
        }
        
        
        return 1;
    }
    
}
