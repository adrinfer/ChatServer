/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import es.chatserver.controllers.viewcontrollers.ServerGuiController;
import es.chatserver.views.Decorator;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


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
        scene.getStylesheets().add(getClass().getResource("/es/chatserver/styles/styles.css").toExternalForm());
    }
    
    private static Stage primaryStage;
    
    public static Stage getPrimaryStage()
    {
        return primaryStage;
    }
    
    public static void setPrimaryStage(Stage stage)
    {
        Main.primaryStage = stage;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        setPrimaryStage(stage);
        //Crear así el controlador para tener acceso al stage
        ServerGuiController controllerGUI = ServerGuiController.getInstance();
        
        //Cargar FXML principal        
        loader = new FXMLLoader(getClass().getResource("/es/chatserver/views/serverGUI.fxml"));
        
        //Asignar el controlador
        loader.setController(controllerGUI);
        root = loader.load();
        
        stage.setTitle("ChatTo: Server");

        //Decorador con los botones minimizar, maximizar, cerrar y redimensionar
        Decorator decoratorRoot = new Decorator(stage, root);
        
        scene = new Scene(decoratorRoot);
        scene.setFill(Color.TRANSPARENT);
        
        stage.initStyle(StageStyle.TRANSPARENT); 
        
        
        loadStyles();
        
        
        stage.setScene(scene);
        
           
        //Transición al iniciar la aplicación    
        ScaleTransition scaleTransitionOpen = ScaleTransitionBuilder.create()
            .node(decoratorRoot)
            .duration(Duration.seconds(0.3))
            .fromX(0)
            .fromY(0)
            .toX(1)
            .toY(1)
            .build();

        stage.show();
        
        //Llamar a la animación después de mostrar el show
        scaleTransitionOpen.play();
        setBindings();
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
