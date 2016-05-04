/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;



import com.google.gson.Gson;
import es.chatserver.logic.Controller;
import es.chatserver.server.messages.NetworkMessage;
import es.chatserver.utils.Status;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrinfer
 */
public class Server implements Callable {
    
    
    //ID único para los clientes que van entrando
    private static int uniqueID;
    
    //Clientes máximos conectados
    private final int MAX_CONECTIONS = 2;
    
    //Puerto del servidor
    private final int port;
    
    //Fluejos
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    
    //Socket cliente y servidor
    private Socket clientConexion;
    private ServerSocket serverSocket;
    
    
    private final ExecutorService executorThread;
    private final ArrayList<Future> futureList;
    private final ArrayList<ClientThread> clientList;
    
    //Instancia del controlador
    private final Controller logicController;
    
    //Primer mensaje recibido del cliente
    private NetworkMessage firstRequest;
    
    //Gson para leer y escribir 
    private final Gson gson;
    
    private boolean serverRunning;
    
    //Constructor
    public Server(int port)
    {
        this.logicController = Controller.getInstance();
        this.port = port;
        this.clientList = new ArrayList();
        this.executorThread = Executors.newFixedThreadPool(MAX_CONECTIONS);
        this.futureList = new ArrayList();
        this.serverRunning = true;
        this.gson = new Gson();
                
    }
    
    
    //Inicializar
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
                
                dataInputStream = new DataInputStream( clientConexion.getInputStream());
                dataOutputStream = new DataOutputStream(clientConexion.getOutputStream());
                
                attendRequest();
                
                
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
    
    
    //Atender nuevas peticiones
    private void attendRequest()
    {
        try 
        {
   
            String json = dataInputStream.readUTF();
            firstRequest = gson.fromJson(json, NetworkMessage.class);
            
            System.out.println("NET: " + firstRequest.getType());
            
            //La primera petición debería ser registro y/o login
            int result = logicController.processRequest(firstRequest);
            
            if(result == Status.LOGIN_OK)
            {
                
                ClientThread newClient = new ClientThread(clientConexion, dataInputStream, dataOutputStream, ++uniqueID);
            
                clientList.add(newClient); //Guardar cliente

                futureList.add(executorThread.submit(newClient));
                
            }
            
            //TODO lanzar thread con executeService para clientes
            
            
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
 
    
    
    @Override
    public Integer call() throws Exception {

        init();
        

        return 13;
    }
    
    
    

}