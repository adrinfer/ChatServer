/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;

import es.chatserver.model.UsersJpaController;
import es.chatserver.styles.UserLabel;
import es.chatserver.utils.Utils;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
    
    @FXML
    private TitledPane titledPaneUsersList;
    
    @FXML
    private VBox rightPane;
    
    @FXML
    private TextArea textArea;
    
    @FXML
    private TitledPane titledPaneUsers;
    
    @FXML
    private VBox vBoxUserList;
   
    @FXML
    private Label lblStatus;

    
    @FXML
    private ScrollPane scrollPaneUsers;
    
    @FXML
    private ToggleButton butStartStop;
    
    private String server = null;
    
    private final Stage stage;
    
    //Constructor, se obtiene el stage
    public ServerGuiController(final Stage stage)
    {
        this.stage = stage;
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
       
        
        leftAccordion.minWidthProperty().bind(leftPane.widthProperty().subtract(14));
        leftAccordion.maxWidthProperty().bind(leftPane.widthProperty().subtract(14));
        leftAccordion.prefWidthProperty().bind(leftPane.widthProperty().subtract(14));
        
        
        //rightPane - width binding
//        rightPane.minWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.maxWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.prefWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
        
        //rightPane - heightProperty
//        rightPane.minHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.maxHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.prefHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        
  
        //lblTitleUsers - width binding
        titledPaneUsersList.minWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneUsersList.maxWidthProperty().bind(leftAccordion.widthProperty());
        titledPaneUsersList.prefWidthProperty().bind(leftAccordion.widthProperty());
        
        
        //scrollPaneUsers - width binding - contiene la lista de usuarios
        scrollPaneUsers.minWidthProperty().bind(titledPaneUsersList.widthProperty());
        scrollPaneUsers.maxWidthProperty().bind(titledPaneUsersList.widthProperty());
        scrollPaneUsers.prefWidthProperty().bind(titledPaneUsersList.widthProperty());
        
        //vBox que almacena los label del listado de usuarios (en el leftPane)
        vBoxUserList.minWidthProperty().bind(scrollPaneUsers.widthProperty());
        vBoxUserList.maxWidthProperty().bind(scrollPaneUsers.widthProperty());
        vBoxUserList.prefWidthProperty().bind(scrollPaneUsers.widthProperty());
        
        

        
        //scrollPaneUsers - height binding - contiene la lista de usuarios
//        scrollPaneUsers.minHeightProperty().bind(titledPaneUsersList.heightProperty());
//        scrollPaneUsers.maxHeightProperty().bind(titledPaneUsersList.heightProperty());
//        scrollPaneUsers.prefHeightProperty().bind(titledPaneUsersList.heightProperty());
        
    }
    
    
    private void init()
    {
        //Hacer que la ventana(stage), se pueda arrastrar con el topPane y bottomPane
        Utils.makeDraggable(stage, topPane);
        Utils.makeDraggable(stage, bottomPane);
        
        //Inicializar unión de los paneles y su ajuste (interfaz gráfica)
        setBindings();
        
        //Quitar barra de scroll horizontal de la lista de usuarios
        scrollPaneUsers.setHbarPolicy(ScrollBarPolicy.NEVER);
        
        //Establecer color de los paneStatus iniciales
        topPaneStatus.getStyleClass().add("stopped");
        bottomPaneStatus.getStyleClass().add("stopped");
        
        //Bloquear el textArea para solo lectura
        textArea.setEditable(false);

        UsersJpaController a = new UsersJpaController();
        System.out.println(a.getUsersCount());

        Label l;
        for(int x = 0; x <= 50; x++)
        {
            l = new UserLabel("Texto label: " + x , vBoxUserList);

            vBoxUserList.getChildren().add(l);
            
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }    
    
}
