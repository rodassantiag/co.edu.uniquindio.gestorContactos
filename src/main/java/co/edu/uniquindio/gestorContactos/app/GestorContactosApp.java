package co.edu.uniquindio.gestorContactos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorContactosApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(GestorContactosApp.class.getResource("/panel.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Gestor Contactos");
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(GestorContactosApp.class, args);
    }

}
