/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;



import com.google.gson.Gson;
import es.chatserver.server.messages.NetworkMessage;
import es.chatserver.logic.Controller;
import es.chatserver.server.messages.LoginMessage;
import es.chatserver.server.messages.RegisterMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author adrinfer
 */
public abstract class ServerService implements Callable {
    
    
    private static int uniqueID;
    
    private final int port;
    private DataInputStream objInputStream;
    private DataOutputStream objOutputStream;
    private final int MAX_CONECTIONS = 2;
    private Socket clientConexion;
    private ServerSocket serverSocket;
    
    private final ExecutorService executorThread;
    private final ArrayList<Future> futureList;
    private final ArrayList<ClientThread> clientList;
    
    //Instancia del controlador
    private final Controller logicController;
    
    //Primer mensaje recibido del cliente
    private NetworkMessage firstNetMsg;
    
    private final Gson gson;
    
    private boolean serverRunning;
    
    //Constructor
    public ServerService(int port)
    {
        this.logicController = Controller.getInstance();
        this.port = port;
        this.clientList = new ArrayList();
        this.executorThread = Executors.newFixedThreadPool(MAX_CONECTIONS);
        this.futureList = new ArrayList();
        this.serverRunning = true;
        this.gson = new Gson();
                
    }
    
    private void init()
    {
        try {
            
            serverSocket = new ServerSocket(port);
            
            while(serverRunning) 
            {
                //Esperar nueva conexión
                System.out.println("ESPERANDO CONEXIÓN");
                clientConexion = serverSocket.accept();
                
                if(!serverRunning)
                {
                    break;
                }
                
                objInputStream = new DataInputStream( clientConexion.getInputStream());
                objOutputStream = new DataOutputStream(clientConexion.getOutputStream());
                
                //El primer mensaje solo deberia ser register o login
                //firstNetMsg = (NetworkMessage) objInputStream.readObject();
                
                //process(firstNetMsg);
                
                String json = objInputStream.readUTF();
                NetworkMessage net = gson.fromJson(json, NetworkMessage.class);
//              firstNetMsg = mapper.readerFor(NetworkMessage.class).readValue(clientConexion.getInputStream());
                System.out.println("NET: " + net.getAlgo());
                
                //TODO lanzar thread con executeService para clientes
                //ClientThread newClient = new ClientThread(clientConexion, objInputStream, objOutputStream, ++uniqueID);
                
                //clientList.add(newClient); //Guardar cliente
                
                //futureList.add(executorThread.submit(newClient));
                
                
            }
            
            
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
 
        }
        finally
        {
            
            if(serverSocket != null && !serverSocket.isClosed()) 
            {
                try 
                {
                    serverSocket.close();
                } 
                catch (IOException ex)
                {
                    ex.printStackTrace(System.err);
                }
            }
            
        }
    }
    
    public void interrupt()
    {
        this.serverRunning = false;       
        
        if(!serverSocket.isClosed())
        {
            try
            {
                this.serverRunning = false;
                
                for(ClientThread s: clientList)
                {
                    s.interrupt();
                }
                
                this.serverSocket.close();

            }
            catch(Exception e)
            {
                System.out.println("Fallo al cerrar el servidor.");
                e.printStackTrace();
            }
        }
    }
    
    
    public void removeClient(int id)
    {
        
    }
    
    
    public void process(NetworkMessage msg)
    {
        
        if(msg instanceof RegisterMessage)
        {
            System.out.println("REGISTER MESSAGE");
        }
        
        if(msg instanceof LoginMessage)
        {
            System.out.println("REGISTER MESSAGE2");
        }
        
        
    }
    
    
    @Override
    public Integer call() throws Exception {

        init();
        

        return 13;
    }
    
    
    

}
