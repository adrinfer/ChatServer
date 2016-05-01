/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;


import chatserver.Main;
import es.chatserver.controllers.persistence.PersistenceController;
import es.chatserver.entities.TextMsg;
import es.chatserver.interfaces.Observer;
import es.chatserver.logic.Controller;
import es.chatserver.model.Client;
import es.chatserver.entities.UserLabel;
import es.chatserver.utils.Utils;
import java.awt.Rectangle;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


/**
 *
 * @author adrinfer
 */
public class ServerGuiController implements Initializable, Observer {
    
    
    @FXML
    private BorderPane mainBorderPane;
    
    @FXML
    private HBox topPaneStatus;

    @FXML
    private HBox bottomPaneStatus;
    
    @FXML
    private Rectangle bottomRecStatus;
    
    @FXML
    private VBox topPane;
    
    @FXML
    private VBox bottomPane;
    
    @FXML
    private HBox bottomStatusPane;
    
    @FXML
    private VBox leftPane;
    
    @FXML
    private Accordion leftAccordion;
    
    
    // ----- LISTADO DE USUARIOS ONLINE ----- //
    @FXML
    private TitledPane titledPaneUsersList;
    
    @FXML
    private VBox vBoxUsersList;
    
    @FXML
    private TextField txtUserFilter;
    
    @FXML
    private ListView listViewUserList;
    
    
    // ----- LISTADO DE USUARIOS TOTALES ----- //
    @FXML
    private TitledPane titledPaneTotalUsersList;
    
    @FXML
    private VBox vBoxTotalUsersList;
    
    @FXML
    private TextField txtTotalUserFilter;
    
    @FXML
    private ListView listViewTotalUserList;
    
    
    // ----- NUEVO USUARIO ----- //
    
    @FXML
    private TitledPane titledPaneNewUser;
    
    @FXML
    private VBox vBoxNewUser;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtNick;
    
    @FXML
    private TextField txtPassword;
    
    @FXML
    private TextField txtEmail;
    
    
    @FXML
    private VBox rightPane;
    
    @FXML
    private Pane centerPane;
    
    @FXML
    private VBox vBoxMessageList;
    
    public VBox getVboxMessageList()
    {
        return vBoxMessageList;
    }
    
    @FXML
    private ScrollPane scrollPaneMessageList;
    
    @FXML
    private TitledPane titledPaneUsers;

   
    @FXML
    private Label lblStatus;

    @FXML
    private ScrollPane scrollPaneUsers;
    
    @FXML
    private ToggleButton butStartStop; 

    
    @FXML
    private Button btnRegistrar;
    
    
    private List<String> filterMessageList;
    private String server = null;
    
 
    //Instancia del PersistenceControler (acceso a JPA's)
    private final PersistenceController perController;
    private Controller logicController = null;   
    
    //Instancia de la clase (Singleton)
    private static ServerGuiController instance = null;
    private final static Lock INSTANCIATION_LOCK = new ReentrantLock();
    
    
    //Constructor por defecto
    private ServerGuiController()
    {
        logicController = Controller.getInstance();
        perController = PersistenceController.getInstance();
        
        //Añadir al controlador el observador de esta clase
        //(Para atender cambios)
        logicController.addObserver(this);
        
        
    }
    
    //Singleton para la instancia
    public static ServerGuiController getInstance()
    {
        
         if(instance == null)
         {
            INSTANCIATION_LOCK.lock();

            try
            {
                if(instance == null) //Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
                {
                    instance = new ServerGuiController();
                }
            }
            finally
            {
                INSTANCIATION_LOCK.unlock();
            }
        }
        return instance;

    }
    
    
    private void startStopButton()
    {
                
        if(server != null)
        {
            //server.stop();
            server = null;
            butStartStop.setText("Start");
            //textArea.setText(textArea.getText() + "\n" + "*** Server: Stop");
            lblStatus.setText("Server: Parado");
            topPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D2090C"), CornerRadii.EMPTY, Insets.EMPTY)));
            bottomStatusPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D2090C"), CornerRadii.EMPTY, Insets.EMPTY)));
            
            
        }
        else
        {
           server = "A";
            butStartStop.setText("Stop");
            //textArea.setText(textArea.getText() + "\n" + "*** Server: Start");
            lblStatus.setText("Server: Iniciado");
            topPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#017810"), CornerRadii.EMPTY, Insets.EMPTY)));
            bottomStatusPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#017810"), CornerRadii.EMPTY, Insets.EMPTY)));
            
        }
    }
    

    //Hacer bindings a los elementos
    private void setBindings()
    {    
                
        scrollPaneMessageList.minHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        scrollPaneMessageList.maxHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        scrollPaneMessageList.prefHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
               
        vBoxMessageList.minWidthProperty().bind(scrollPaneMessageList.widthProperty());
        vBoxMessageList.maxWidthProperty().bind(scrollPaneMessageList.widthProperty());
        vBoxMessageList.prefWidthProperty().bind(scrollPaneMessageList.widthProperty());
        

        //leftPane - widthProperty
        leftPane.minWidthProperty().bind(mainBorderPane.minWidthProperty().multiply(0.25));
        leftPane.maxWidthProperty().bind(mainBorderPane.maxWidthProperty().multiply(0.25));
        leftPane.prefWidthProperty().bind(mainBorderPane.prefWidthProperty().multiply(0.25));
        
        
        
        //leftPane - heightProperty
        leftPane.minHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        leftPane.maxHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        leftPane.prefHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
       
        
        //Acordeon dentro del leftPane
        leftAccordion.minWidthProperty().bind(leftPane.widthProperty().subtract(15));
        leftAccordion.maxWidthProperty().bind(leftPane.widthProperty().subtract(15));
        leftAccordion.prefWidthProperty().bind(leftPane.widthProperty().subtract(15));
        
        

        // ----- BIND APARTADO USUARIOS ONLINE ----- //
  
        //Ajustar el titlePane a su padre leftAcordion (el cual tiene su binding)
        titledPaneUsersList.minWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneUsersList.maxWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneUsersList.prefWidthProperty().bind(leftAccordion.widthProperty());
        
        
        //Bind del VBox donde esta el TextField para filtrar, y el ListView de lista de usuarios
        vBoxUsersList.minWidthProperty().bind(titledPaneUsersList.minWidthProperty());
        vBoxUsersList.maxWidthProperty().bind(titledPaneUsersList.maxWidthProperty());
        vBoxUsersList.prefWidthProperty().bind(titledPaneUsersList.prefWidthProperty());
        
        
        
        //Bind del TextField para filtrar en la lista de usuarios
        //El substract es para separarlo de los lados (en sceneBuilder se mueve 10 px a la derecha)
        txtUserFilter.minWidthProperty().bind(vBoxUsersList.minWidthProperty().subtract(35));
        txtUserFilter.maxWidthProperty().bind(vBoxUsersList.maxWidthProperty().subtract(35));
        txtUserFilter.prefWidthProperty().bind(vBoxUsersList.prefWidthProperty().subtract(35));
        
        
        //ListView que almacena los label del listado de usuarios (en el leftPane)
        listViewUserList.minWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
        listViewUserList.maxWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
        listViewUserList.prefWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
        
        // ----- FIN BIND APARTADO USUARIOS ONLINE ----- //
        
        
        
        // ----- BIND APARTADO USUARIOS TOTALES ----- //
        
        //Ajustar el titlePane a su padre leftAcordion (el cual tiene su binding)
        titledPaneTotalUsersList.minWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneTotalUsersList.maxWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneTotalUsersList.prefWidthProperty().bind(leftAccordion.widthProperty());
        
        
        //Bind del VBox donde esta el TextField para filtrar, y el ListView de lista de usuarios
        vBoxTotalUsersList.minWidthProperty().bind(titledPaneUsersList.minWidthProperty());
        vBoxTotalUsersList.maxWidthProperty().bind(titledPaneUsersList.maxWidthProperty());
        vBoxTotalUsersList.prefWidthProperty().bind(titledPaneUsersList.prefWidthProperty());
        
        
        //Bind del TextField para filtrar en la lista de usuarios
        //El substract es para separarlo de los lados (en sceneBuilder se mueve 10 px a la derecha)
        txtTotalUserFilter.minWidthProperty().bind(vBoxUsersList.minWidthProperty().subtract(35));
        txtTotalUserFilter.maxWidthProperty().bind(vBoxUsersList.maxWidthProperty().subtract(35));
        txtTotalUserFilter.prefWidthProperty().bind(vBoxUsersList.prefWidthProperty().subtract(35));
        
        
        //ListView que almacena los label del listado de usuarios (en el leftPane)
        listViewTotalUserList.minWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
        listViewTotalUserList.maxWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
        listViewTotalUserList.prefWidthProperty().bind(titledPaneUsersList.widthProperty().subtract(15));
                
        // ----- FIN BIND APARTADO USUARIOS TOTALES ----- //
        
             
        
         //rightPane - width binding
        rightPane.minWidthProperty().bind(mainBorderPane.minWidthProperty().multiply(0.2));
        rightPane.maxWidthProperty().bind(mainBorderPane.maxWidthProperty().multiply(0.2));
        rightPane.prefWidthProperty().bind(mainBorderPane.prefWidthProperty().multiply(0.2));
        
        //rightPane - heightProperty
        rightPane.minHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        rightPane.maxHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        rightPane.prefHeightProperty().bind(mainBorderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        
    }
    
    //Inicializar listeners
    private void initEvents()
    {
               
        //Evento que capta cuando se escribe en el filtro de USUARIOS ONLINE
        txtUserFilter.setOnKeyReleased((event) -> { 
            refreshOnlineUsers();
        });

        //Evento que capta cuando se escribe en el filtro de USUARIOS TOTALES
        txtTotalUserFilter.setOnKeyReleased((event) -> {          
            refreshTotalUsers();
        });
              
        //Acción del botón para añadir nuevo usuario
        btnRegistrar.setOnAction((event) -> 
                logicController.addUser(txtNombre.getText(), txtNick.getText(), txtPassword.getText(), txtEmail.getText())
        );
    }
    
 
    
    //ListView - Mostrar usuarios online
    public void refreshOnlineUsers()
    {
        
        //El logicController se encarga de usar el posible filtro
        List<Client> clientList = logicController.getUsersList(txtUserFilter.getText());
        listViewUserList.getItems().clear();
        int count = 0;
        if(clientList.isEmpty())
        {
            listViewUserList.getItems().add("       Sin resultados");
            titledPaneUsersList.setText("Usuarios online: " + count);
            
        }
        else
        {
            for(Client client: clientList)
            {
                listViewUserList.getItems().add(new UserLabel(client, listViewUserList));
                count++;
            }

            titledPaneUsersList.setText("Usuarios online: " + count);
            
        }

    }
    

    //ListView - Mostrar usuarios totales
    private void refreshTotalUsers()
    {
        
        //El logicController se encarga de usar el posible filtro
        List<Client> clientList = logicController.getUsersList(txtTotalUserFilter.getText());
        listViewTotalUserList.getItems().clear();
        
        int count = 0;
        
        
        if(clientList.isEmpty())
        {
            listViewTotalUserList.getItems().add("      Sin resultados");
            titledPaneTotalUsersList.setText("Usuarios totales: " + count);
        }
        else
        {
            for(Client client: clientList)
            {
                listViewTotalUserList.getItems().add(new UserLabel(client, listViewTotalUserList));
                count++;
            }
            
            titledPaneTotalUsersList.setText("Usuarios totales: " + count);
            
        }
    }
    
    
 
    @Override
    public void update() {
        refreshOnlineUsers();
        refreshTotalUsers();
    }
    
    
    @Override
    //Mediente patrón observador, se recibe mensaje 
    public void update(TextMsg message) {
        
        refreshOnlineUsers();
        refreshTotalUsers();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: red;");
        
        
        vBoxMessageList.getChildren().add(message.getMessage());
        
        //Una vez puesto el mensaje hacer scroll abajo del todo
        //Es necesario esto para que le de tiempo y haga scroll correctamente
        Platform.runLater(() -> {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            scrollPaneMessageList.setVvalue(1);
        });
        
    }
    
    private void init()
    {
   
        
        //Hacer que la ventana(stage), se pueda arrastrar con el topPane y bottomPane
        Utils.makeDraggable(Main.getPrimaryStage(), topPane);
        Utils.makeDraggable(Main.getPrimaryStage(), bottomPane);
        
        //Inicializar unión de los paneles y su ajuste (interfaz gráfica)
        setBindings();
        
        //Inicializar eventos
        initEvents();
        
        //Actualizar listas de usuarios
        refreshOnlineUsers();
        refreshTotalUsers();
        
        //Quitar la barra de scroll horizontal de la lista de mensajes
        scrollPaneMessageList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
            
        //Establecer color de los paneStatus iniciales
        topPaneStatus.getStyleClass().add("stopped");
        bottomPaneStatus.getStyleClass().add("stopped");
        
          
    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }  
    
    
}
