/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;

import es.chatserver.model.Client;
import es.chatserver.utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author adrinfer
 */
public class ModifyUserGUIController implements Initializable {
    
    
    @FXML
    private HBox topPane;
    
    @FXML
    private Label userId;
    
    //Atributos
    private Client client; //Cliente de la ventana de modificación
    private Stage stage;
    
    private static ModifyUserGUIController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    
    
    public static ModifyUserGUIController getInstance()
    {
        
         if(instance == null)
         {
            INSTANCIATION_LOCK.lock();

            try
            {
                if(instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new ModifyUserGUIController();
                }
            }
            finally
            {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }
    
    private ModifyUserGUIController()
    {
        
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
        Utils.makeDraggable(this.stage, topPane);
    }

    public Stage getStage()
    {
        return stage;
    }
    
    public void setClient(Client client)
    {
        
        this.client = client;
        update();
    }
    
    private void update()
    {
        userId.setText(client.getNick());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
