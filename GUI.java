import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("League Muter");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("password:");
        grid.add(pw, 0, 2);

        PasswordField passTextField = new PasswordField();
        grid.add(passTextField, 1, 2);

        Label muteMessage = new Label("Mute Message:");
        grid.add(muteMessage, 0, 3);

        TextField muteMsgTextField = new TextField();
        grid.add(muteMsgTextField, 1, 3);


        Label apiKey = new Label("API Key:");
        grid.add(apiKey, 0, 4);

        TextField apiKeyTextField = new TextField();
        grid.add(apiKeyTextField, 1, 4);

        Button btn = new Button("press me to mute once your game is loading");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6);
        grid.setColumnSpan(actiontarget, 2);
        grid.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        //mute button action
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                String username = userTextField.getText();
                String password = passTextField.getText();
                String muteMsg = muteMsgTextField.getText();
                String apiKey = apiKeyTextField.getText();
                ChatUser chatUser = new ChatUser(username, password, apiKey);
                List<User> friends = chatUser.getFriends();
                
            }
        });

        Scene scene = new Scene(grid, 600, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

