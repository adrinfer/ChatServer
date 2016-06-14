/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.server;

import com.google.gson.Gson;
import es.chatserver.logic.Controller;
import es.chatserver.model.Client;
import es.chatserver.model.ClientConver;
import es.chatserver.server.messages.ConverData;
import es.chatserver.server.messages.ConversDataMessage;
import es.chatserver.server.messages.Message;
import es.chatserver.server.messages.NetworkMessage;
import es.chatserver.server.messages.requests.RequestMessage;
import es.chatserver.utils.Status;
import es.chatserver.utils.Utils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Adrián Fernández Cano
 */
public class ClientThread implements Callable<Integer> {
    
    //Variable de estado para controlar la vida del hilo
    private boolean running;
    private final int uniqueID;
    private final Socket clientConexion;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    
    private boolean firstMessage = true;
    
    private final Controller logicController;
    
    private NetworkMessage request;
    
    //Gson instance - getting from logicController
    private final Gson gson;
    
    public ClientThread(Socket clientConexion, DataInputStream dataInputStream, DataOutputStream dataOutputStream, int id)
    {
        this.uniqueID = id;
        this.clientConexion = clientConexion;
        this.running = true;
        
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.logicController = Controller.getInstance();
        this.gson = logicController.getGson();

        
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
        
        if(clientConexion != null)
        {
            this.clientConexion.close();
        }
        
        
    }

    @Override
    public Integer call() throws Exception {
        
        
        while(running)
        {
            
            //Read from socket
            String readInput = dataInputStream.readUTF();
            
            if(firstMessage)
            {
                //request = gson.fromJson(json, NetworkMessage.class);
                System.out.println("DENTRO CLIENT THREAD -> ");
                RequestMessage firstRequestMessage = logicController.getGson().fromJson(readInput, RequestMessage.class);
                
                //Must be GET_DATA type
                if(firstRequestMessage.getRequestType() == RequestMessage.GET_DATA)
                {
                    
                    Client requestOwner = logicController.findClient(firstRequestMessage.getUserNick());
                    
                    ConversDataMessage conversData = new ConversDataMessage();
                    
                    List<ConverData> converDataList = new ArrayList();
                    
                    List<ClientConver> clientConverList = logicController.fintClientConverEntities();
                    
                    for(ClientConver clientConver: clientConverList)
                    {
                        
                        List<Message> messageList = new ArrayList();
                        
                        if(clientConver.getClient().getNick().equals(firstRequestMessage.getUserNick()))
                        {
                            System.err.println("CLIENT CONVER: " + clientConver);
                            ConverData converData = new ConverData();
                            converData.setConverID(String.valueOf(clientConver.getConver().getId()));
                            converData.setConverName(clientConver.getConver().getName());
                            
                            List<es.chatserver.model.Message> modelMsgList = logicController.findMessagesFilterByConver(clientConver.getConver().getId());
                            
                            
                            for(es.chatserver.model.Message msg: modelMsgList)
                            {
                                Message newMsg = new Message();
                                newMsg.setMsgID(String.valueOf(msg.getId()));
                                newMsg.setMsgType(String.valueOf(msg.getType()));
                                newMsg.setMsgText(msg.getText());
                                newMsg.setUserNick(msg.getClientNick());
                                
                                if(msg.getDate() != null)
                                {
                                   newMsg.setMsgDate(Utils.getDfMessage().format(msg.getDate())); 
                                }
                                
                                newMsg.setClientId(String.valueOf(msg.getClientConver().getClient().getId()));
                                newMsg.setClientId(String.valueOf(msg.getClientConver().getConver().getId()));
                                messageList.add(newMsg);
                            }
                            
                            converData.setConverMessages(messageList);
                            
                            converDataList.add(converData);
                            
                        }
                        
                    }
                    
                    conversData.setConverDataArray(converDataList);
                    
                    
                    this.dataOutputStream.writeUTF(logicController.getGson().toJson(conversData));
                    
                }
                else
                {
                    this.dataOutputStream.writeUTF(String.valueOf(Status.ERROR));
                }
                
                
                //Set false the firstMessage
                firstMessage = false;
                readInput = "no te preocupes";
            }
            else
            {
                System.err.println("READ INPUT: " + readInput);
                Message inputMessage = logicController.getGson().fromJson(readInput, Message.class);

                es.chatserver.model.Message modelMessage = new es.chatserver.model.Message();
                
                Client inputMsgClient = logicController.findClient(inputMessage.getUserNick());
                
                //modelMessage.setId(Integer.valueOf(inputMessage.getMsgID()));
                modelMessage.setText(inputMessage.getMsgText());
                modelMessage.setType(Integer.valueOf(inputMessage.getMsgType()));

                ClientConver clientConver = logicController.findClientConver(String.valueOf(inputMsgClient.getId()), inputMessage.getConverId());

                modelMessage.setClientConver(clientConver);
                modelMessage.setDate(new Date());
                modelMessage.setClientNick(inputMessage.getUserNick());

                logicController.persist(modelMessage);
            }
            
            

            //The rest of messages
            //System.err.println("ESPERANDO RESTO MENSAJES EN CLIENT THREAD");
        }
        
        
        return 1;
    }
    
}
