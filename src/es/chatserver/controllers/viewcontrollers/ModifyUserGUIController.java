/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;

import es.chatserver.entities.TextMsg;
import es.chatserver.logic.Controller;
import es.chatserver.model.Client;
import es.chatserver.utils.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtNick;
    
    @FXML
    private TextField txtPass;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private CheckBox checkBoxBloqueado;
    
    @FXML
    private Button btnDiscard;
    
    @FXML
    private Button btnSave;
    
    
    //Atributos
    private Client client; //Cliente de la ventana de modificación
    private Stage stage;
    
    private Controller logicController;
    
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
    
    private void init()
    {
        btnDiscard.setOnAction((event) -> {
            discard();
        });
        
        btnSave.setOnAction((event) -> {
            saveClient(client);
        });

    }
    
    private ModifyUserGUIController()
    {
        
        logicController = Controller.getInstance();
        
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
    
    
    //Establecer cliente de la modificación y sus datos
    public void setClient(Client client)
    {
          
        this.client = client;
        userId.setText("ID: " + client.getNick());
        txtNombre.setText(client.getNombre());
        txtNick.setText(client.getNick());
        txtPass.setText(client.getPass());
        txtEmail.setText(client.getEmail());
        
        if(client.getBloqueado())
        {
            checkBoxBloqueado.setSelected(true);
        }
        else
        {
            checkBoxBloqueado.setSelected(false);
        }
        
        //Traer la ventana al frente
        this.stage.toFront();
               
    }
    
    //Descartar
    private void discard()
    {
        stage.close();
    }
    
    
    //Modificar cliente
    private void saveClient(Client client)
    {
        
        //Modificar datos
        client.setNombre(txtNombre.getText());
        client.setNick(txtNick.getText());
        client.setPass(txtPass.getText());
        client.setEmail(txtEmail.getText());
        
        
        //Para bloquear y desbloquear usar los métodos ya creados
        if(checkBoxBloqueado.isSelected())
        {
            logicController.lockClient(client);
        }
        else
        {
            logicController.unlockClient(client);
        }
         
        //TODO VER ESTE METODO
        logicController.modifyUser(client);
         
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

 
}
