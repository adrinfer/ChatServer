/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatserver.controllers.viewcontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    
     class Delta { 
         double x, y;   
    }
     
    final Delta dragDelta = new Delta();
    
    @FXML
    private BorderPane mainBorderPane;
        
    @FXML
    private HBox topPane;
    
    @FXML
    private BorderPane bottomPane;
    
    @FXML
    private HBox bottomStatusPane;
    
    @FXML
    private VBox leftPane;
    
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
//        leftPane.minWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        leftPane.maxWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        leftPane.prefWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
        
        
        //leftPane - heightProperty
//        leftPane.minHeightProperty().bind(mainBorderPane.heightProperty().subtract(20).subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        leftPane.maxHeightProperty().bind(mainBorderPane.heightProperty().subtract(20).subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        leftPane.prefHeightProperty().bind(mainBorderPane.heightProperty().subtract(20).subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
       
        
        //rightPane - width binding
//        rightPane.minWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.maxWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
//        rightPane.prefWidthProperty().bind(mainBorderPane.widthProperty().multiply(0.2));
        
        //rightPane - heightProperty
//        rightPane.minHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.maxHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
//        rightPane.prefHeightProperty().bind(borderPane.heightProperty().subtract(topPane.heightProperty().add(bottomPane.heightProperty())));
        
  
        //lblTitleUsers - width binding
//        titledPaneUsers.minWidthProperty().bind(leftPane.widthProperty());
//        titledPaneUsers.maxWidthProperty().bind(leftPane.widthProperty());
//        titledPaneUsers.prefWidthProperty().bind(leftPane.widthProperty());
        
        
        //vBox que almacena los label del listado de usuarios (en el leftPane)
//        vBoxUserList.minWidthProperty().bind(scrollPaneUsers.widthProperty());
//        vBoxUserList.maxWidthProperty().bind(scrollPaneUsers.widthProperty());
//        vBoxUserList.prefWidthProperty().bind(scrollPaneUsers.widthProperty());
        
        
        //scrollPaneUsers - height binding - contiene la lista de usuarios
//        scrollPaneUsers.minHeightProperty().bind(leftPane.heightProperty().subtract( titlePaneUsersList.heightProperty() ));
//        scrollPaneUsers.maxHeightProperty().bind(leftPane.heightProperty().subtract( titlePaneUsersList.heightProperty() ));
//        scrollPaneUsers.prefHeightProperty().bind(leftPane.heightProperty().subtract( titlePaneUsersList.heightProperty() ));
//        
    }
    
    
    private void init()
    {
        
//        borderPane.getStyleClass().add("borde");
//        
//        textArea.setEditable(false);
//        setBindings();
//
//      leftPane.setAlignment(Pos.BASELINE_RIGHT);
//
//        scrollPaneUsers.setHbarPolicy(ScrollBarPolicy.NEVER);
//
//        
//        lblStatus.setText("Server: Parado");
//        butStartStop.setText("Start");
//        //Listener startStop server button
//        butStartStop.setOnAction((event) -> {
//            startStopButton();        
//        });
//        
//        Label l;
//        for(int x = 0; x <= 50; x++)
//        {
//            l = new Label("Texto label: " + x );
//            l.setPadding(new Insets(0, 0, 0, 20));
//            l.setStyle("-fx-background-color: white;");
//            vBoxUserList.getChildren().add(l);
//        }
        
        
        topPane.setOnMousePressed((event) -> {
            dragDelta.x = stage.getX() - event.getScreenX();
            dragDelta.y = stage.getY() - event.getScreenY();
        });
        
        topPane.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() + dragDelta.x);
            stage.setY(event.getScreenY() + dragDelta.y);
        });
        
        topPane.setOnMouseReleased((event) -> {
            stage.setX(event.getScreenX() + dragDelta.x);
            stage.setY(event.getScreenY() + dragDelta.y);
        });
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }    
    
}
