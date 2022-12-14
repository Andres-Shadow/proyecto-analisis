
package Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacion extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/Vista/InterpolacionInversa.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/RungeKutta.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Interpolacion Inversa");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
