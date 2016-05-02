/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author adrinfer
 */
public class ServerRun {
    
    private final ExecutorService executorServer;
    private final Server server;
    
    
    public ServerRun(int port)
    {
        executorServer =  Executors.newSingleThreadExecutor();
        this.server = new Server(port);
        executorServer.submit(server);
    }
    
    public void interrupt()
    {
        
        this.server.interrupt();
        this.executorServer.shutdownNow();
        
    }
    
}
