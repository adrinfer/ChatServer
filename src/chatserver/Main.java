/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import es.chatserver.controllers.viewcontrollers.ServerGuiController;
import es.chatserver.views.Decorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author Practicas01
 */
public class Main extends Application {
    
    //Atributos
    private BorderPane root;
    private Scene scene;
    FXMLLoader loader;
    
    //Debe ser llamado después del "show" del stage principal
    private void setBindings()
    {
        //Width
        root.minWidthProperty().bind(scene.widthProperty().subtract(1));
        root.maxWidthProperty().bind(scene.widthProperty().subtract(1));
        root.prefWidthProperty().bind(scene.widthProperty().subtract(1));
        
        //Height
        root.minHeightProperty().bind(scene.heightProperty().subtract(1));
        root.maxHeightProperty().bind(scene.heightProperty().subtract(1));
        root.prefHeightProperty().bind(scene.heightProperty().subtract(1));
    }
    
    //Cargar css utilizados - Debe ser llamado despues de crear la escena.
    private void loadStyles()
    {
        scene.getStylesheets().add(getClass().getResource("/es/chatserver/styles/scrollBar.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/es/chatserver/styles/mainBorderPaneBorders.css").toExternalForm());
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ServerGuiController controllerGUI = new ServerGuiController(stage);
        
        //Cargar FXML principal
        
        loader = new FXMLLoader(getClass().getResource("/es/chatserver/views/serverGUI.fxml"));
        loader.setController(controllerGUI);
        root = loader.load();
        
        Decorator d = new Decorator(stage, root);
        
        scene = new Scene(d);
        
        loadStyles();
        
        stage.setTitle("ChatTo: Server");
        stage.initStyle(StageStyle.TRANSPARENT); 
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        
        setBindings();
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
