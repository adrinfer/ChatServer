/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.entities;


import es.chatserver.controllers.viewcontrollers.ModifyUserGUIController;
import es.chatserver.logic.Controller;
import es.chatserver.model.Client;
import es.chatserver.views.Decorator;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Practicas01
 */
public class UserLabelItem extends MenuItem {
    
    //Atributos
    private final Controller logicController = Controller.getInstance();
    private Client client; //Cliente asociado
    private final String txt;
    
    
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    private FXMLLoader loader;
    private static Stage modifyStage = null;
    private Pane modifyRoot;
    private Scene scene;
    
    //Constructor parametrizado
    public UserLabelItem(String txt)
    {
        super(txt);
        this.txt = txt;
        
        this.getStyleClass().add("userLabel");
        
        this.setOnAction(configHandler());
        
               
        
    }
    
    public void setClient(Client client)
    {
        this.client = client;
    }
    
    
    private EventHandler configHandler()
    {
        EventHandler listener = ((EventHandler) (Event event) -> {
            
            switch (txt.toLowerCase()) {
                case "modificar": //MODIFICAR
                    
                    
                    //Si estaba en hidden mostrarla
                    if(!getModifyStage().isShowing())
                    {
                        getModifyStage().show();
                    }
                    
                    //Mandar a la ventana de modificacion el cliente
                    ModifyUserGUIController.getInstance().setClient(client);
                    ModifyUserGUIController.getInstance().getStage().setTitle("Modificar usuario: " + client.getNick());
                    
                    break;
                    
                case "borrar": //BORRAR
                    
                    logicController.deleteClient(client.getId());
                    
                    break;
                    
                case "bloquear": //BLOQUEAR
                                        
                    logicController.lockClient(client);

                    break;
                    
                case "desbloquear": //BLOQUEAR
                    
                    logicController.unlockClient(client);
                    
                    break;
                    
                default:
                    
                    break;
            }
            
            
        
        });
        
        
        return listener;
        
    }
    
    
    private Stage getModifyStage()
    {
        
        
        if(modifyStage == null)
        {
            INSTANCIATION_LOCK.lock();
            
            try
            {
                if(modifyStage == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    modifyStage = new Stage();
                    
                    //Cargar FXML del modifyStage
                    loader = new FXMLLoader(getClass().getResource("/es/chatserver/views/modifyGUI.fxml"));
                    
                    //Asignar el controlador
                    ModifyUserGUIController modifyUserGUIController = ModifyUserGUIController.getInstance();

                    loader.setController(modifyUserGUIController);
                    modifyRoot = loader.load();
                    
                    //Este metodo usa un componente del FXML
                    // el load() debe ser realizado antes
                    modifyUserGUIController.setStage(modifyStage);
                    
                    
                    
                    //Decorador para boton de cerrar
                    Decorator decoratorModify = new Decorator(modifyStage, modifyRoot);
                    decoratorModify.setResizable(false);
                    decoratorModify.setMaxizable(false);
                    
                    //Cargar el decorador que tiene al panel
                    scene = new Scene(decoratorModify);
                    scene.getStylesheets().add(getClass().getResource("/es/chatserver/styles/styles.css").toExternalForm());
                    modifyStage.setScene(scene);
                    scene.setFill(Color.TRANSPARENT);
                    
                    modifyStage.initStyle(StageStyle.TRANSPARENT);
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(UserLabelItem.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return modifyStage;
    }
    

    
}
