package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double x,y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Send Mail");
        //primaryStage.setScene(new Scene(root));

        final Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() -x);
            primaryStage.setY(event.getScreenY() -y);
        });
        /*root.setOnMouseClicked(event -> {
            primaryStage
        });*/
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
