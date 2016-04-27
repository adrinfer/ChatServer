/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;


import chatserver.Main;
import es.chatserver.controllers.persistence.PersistenceController;
import es.chatserver.model.Client;
import es.chatserver.styles.UserLabel;
import es.chatserver.utils.Utils;
import java.awt.Rectangle;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;


/**
 *
 * @author adrinfer
 */
public class ServerGuiController implements Initializable {
    

    
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
    private TextArea textArea;
    
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
    
    private String server = null;
    
 
    //Instancia del PersistenceControler 
    private final PersistenceController perController = PersistenceController.getInstance();

    
    
    private static ServerGuiController instance = null;
    private final  static  Lock INSTANCIATION_LOCK = new ReentrantLock();
    
    private ServerGuiController()
    {
        
    }
    
    public static ServerGuiController getInstance()
    {
        
         if(instance == null)
         {
            INSTANCIATION_LOCK.lock();

            try
            {
                if (instance == null)//Comprobamos que no se haya inicializado mientras se esperaba al cerrojo
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
            textArea.setText(textArea.getText() + "\n" + "*** Server: Stop");
            lblStatus.setText("Server: Parado");
            topPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D2090C"), CornerRadii.EMPTY, Insets.EMPTY)));
            bottomStatusPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D2090C"), CornerRadii.EMPTY, Insets.EMPTY)));
            
            
        }
        else
        {
           server = "A";
            butStartStop.setText("Stop");
            textArea.setText(textArea.getText() + "\n" + "*** Server: Start");
            lblStatus.setText("Server: Iniciado");
            topPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#017810"), CornerRadii.EMPTY, Insets.EMPTY)));
            bottomStatusPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#017810"), CornerRadii.EMPTY, Insets.EMPTY)));
            
        }
    }
    

    
    private void setBindings()
    {    
        
        //leftPane - widthProperty
        leftPane.minWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.25));
        leftPane.maxWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.25));
        leftPane.prefWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.25));
        
        
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
        
        
        
        vBoxNewUser.minHeightProperty().bind(titledPaneNewUser.minHeightProperty());
        vBoxNewUser.maxHeightProperty().bind(titledPaneNewUser.maxHeightProperty());
        vBoxNewUser.prefHeightProperty().bind(titledPaneNewUser.prefHeightProperty());
        
        //vBox con los text field para crear nuevo usuario
        vBoxNewUser.minWidthProperty().bind(titledPaneNewUser.minWidthProperty());
        vBoxNewUser.maxWidthProperty().bind(titledPaneNewUser.maxWidthProperty());
        vBoxNewUser.prefWidthProperty().bind(titledPaneNewUser.prefWidthProperty());
        
        
        
        
          //rightPane - width binding
//        rightPane.minWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.maxWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.prefWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
        
        //rightPane - heightProperty
//        rightPane.minHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.maxHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.prefHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        
    }
    
    
    private void init()
    {
   
        
        //Hacer que la ventana(stage), se pueda arrastrar con el topPane y bottomPane
        Utils.makeDraggable(Main.getPrimaryStage(), topPane);
        Utils.makeDraggable(Main.getPrimaryStage(), bottomPane);
        
        //Inicializar unión de los paneles y su ajuste (interfaz gráfica)
        setBindings();
        
        //Quitar barra de scroll horizontal de la lista de usuarios
        
        
        //Establecer color de los paneStatus iniciales
        topPaneStatus.getStyleClass().add("stopped");
        bottomPaneStatus.getStyleClass().add("stopped");
        
        //Bloquear el textArea para solo lectura
        textArea.setEditable(false);

        
        btnRegistrar.setOnAction((event) -> 
                this.addUser(txtNombre.getText(), txtNick.getText(), txtPassword.getText(), txtEmail.getText())
        );
        
        List<Client> lista = perController.findClientEntities();
        
        txtUserFilter.setOnKeyReleased((event) -> { 
            
            listViewUserList.getItems().clear();
            if(!txtUserFilter.getText().equals(""))
            { 
                lista.stream().map((cliente) -> new UserLabel(cliente.getNick(), listViewUserList)).forEach((cliente) -> {               
                    if(cliente.getText().contains(txtUserFilter.getText()))
                    {
                        listViewUserList.getItems().add(cliente);
                    }
                }); 
            }
            else
            {
                lista.stream().map((cliente) -> new UserLabel(cliente.getNick(), listViewUserList)).forEach((cliente) -> {        
                    listViewUserList.getItems().add(cliente);
                }); 
            }
        });
        
//        for(int x = 0; x < 30; x++)
//        {
//            Client c = new Client("" + x + "adri " + x, "pass"+x);
//            perController.persist(c);
//        }
        
        
        
             
  
        
        
        
        

    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }  
    
    
    
    
    // ACCIONES
    
    
    //Añadir nuevo usuario
    public void addUser(String nombre, String nick, String pass, String email)
    {
        Client nuevoCliente = new Client(nombre, nick, pass, email);
        perController.persist(nuevoCliente);
        System.out.println("AAAAAAAAAAAAAAAAAa");
    }
    
}
