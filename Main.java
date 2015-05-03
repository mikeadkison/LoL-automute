import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static GUI gui;
    public static void main(String[] args) {
        launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
        gui = new GUI();
        gui.start(primaryStage);
    }
}
