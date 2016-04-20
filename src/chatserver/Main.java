/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.beans.PersistenceDelegate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author Practicas01
 */
public class Main extends Application {
    
    
    private BorderPane root;
    private Scene scene;
    
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
    
    
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/es/chatserver/views/FXMLDocument.fxml"));
        
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/es/chatserver/styles/scrollBar.css").toExternalForm());

        stage.setTitle("AAAA");
        //stage.initStyle(StageStyle.UTILITY);
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
