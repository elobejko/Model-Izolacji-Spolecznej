package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Model Izolacji Społecznej");
        primaryStage.setScene(new Scene(root, 800, 500));
        Controller controller = loader.getController();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        
    }



}
