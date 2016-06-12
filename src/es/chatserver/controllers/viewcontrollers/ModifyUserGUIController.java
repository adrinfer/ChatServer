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
    
    @FXML
    private Label lblValidNick;
    
    @FXML
    private Label lblValidEmail;
    
    @FXML
    private HBox hBoxNick;
    
    @FXML
    private HBox hBoxEmail;
    
 
    
    
    //Atributos
    private Client client; //Cliente de la ventana de modificación
    private Stage stage;
    
    private final Controller logicController;
    
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
        
        
        //Comprobar nick al lliberar la tecla pulsada
        txtNick.setOnKeyReleased((event) -> {
            
            //Comprobar que no sea el que ya habia introducido para que no lo tome como error
            if(txtNick.getText().equals(client.getNick()) || logicController.validateNick(txtNick.getText()))
            {
                btnSave.setDisable(false);
                hBoxNick.setStyle("-fx-background-color: transparent; -fx-spacing: 10;");
                lblValidNick.setText("");
                
            }
            else
            {
                btnSave.setDisable(true);
                hBoxNick.setStyle("-fx-background-color: red; -fx-spacing: 10;");
                lblValidNick.setText("Nick en uso");
            }
            
        });
        
        //Comprobar email al lliberar la tecla pulsada
        txtEmail.setOnKeyReleased((event) -> {
            
            //Comprobar que no sea el que ya habia introducido para que no lo tome como error
            if(txtEmail.getText().equals(client.getEmail()) || logicController.validateEmail(txtEmail.getText()))
            {
                btnSave.setDisable(false);
                hBoxEmail.setStyle("-fx-background-color: transparent; -fx-spacing: 10;");
                lblValidEmail.setText("");
            }
            else
            {
                btnSave.setDisable(true);
                hBoxEmail.setStyle("-fx-background-color: red; -fx-spacing: 10;");
                lblValidEmail.setText("Email en uso o incorrecto");
            }
            event.consume();
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
        
        //Modificar datos - el parámetro int = 1 indica que es modificacion
        boolean validNick = logicController.validateNick(txtNick.getText(), 1);
        boolean validEmail = logicController.validateEmail(txtEmail.getText(), 1);
        
        //msgNotify, muestra el estado de la modificacion
        int msgNotify = TextMsg.USER_MODIFIED;
        
        if(validNick && validEmail)
        {
            
            client.setEmail(txtEmail.getText());
            client.setNick(txtNick.getText());
            msgNotify = TextMsg.USER_MODIFIED;
        }
        else
        {
            
            //Comprobar que no sea el que ya habia introducido para que no lo tome como error
            if(txtEmail.getText().equals(client.getEmail()) || validEmail)
            {
                client.setEmail(txtEmail.getText());
            }
            else
            {
                System.out.println("ENTRO EMAIL USE"); 
                logicController.inform(new TextMsg(client, TextMsg.EMAIL_IN_USE_NOT_MODIFIED));
                msgNotify = TextMsg.USER_PARTIALLY_MODIFIED;
            }


            //Comprobar que no sea el que ya habia introducido para que no lo tome como error
            if(validNick || txtNick.getText().equals(client.getNick()))
            {
                 client.setNick(txtNick.getText());
            }
            else
            {
                System.out.println("ENTRO NICK USE");
                logicController.inform(new TextMsg(client, TextMsg.NICK_IN_USE_NOT_MODIFIED));
                msgNotify = TextMsg.USER_PARTIALLY_MODIFIED;
            }
        }
 
        



        
        client.setPass(txtPass.getText());
        client.setNombre(txtNombre.getText());
        
        //Para bloquear y desbloquear usar el método del cliente
        //Si se utilizan los metodos del controlador se actualiza antes de tiempo
        
        if(client.getBloqueado() != checkBoxBloqueado.isSelected())
        {
            client.setBloqueado(checkBoxBloqueado.isSelected());
            if(client.getBloqueado())
            {
                logicController.inform(new TextMsg(client, TextMsg.USER_LOCK));
            }
            else
            {
                logicController.inform(new TextMsg(client, TextMsg.USER_UNLOCK));
            }
        }
        

        logicController.edit(client, msgNotify);
        
        //TODO VER ESTE METODO
        
        
        
        
         
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

 
}
