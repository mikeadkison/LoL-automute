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
import java.util.prefs.Preferences;

import java.awt.AWTException;

import java.util.Map;
import java.util.HashMap;

public class GUI extends Application {
    private Preferences prefs;
    protected String username; //used to log in
    protected String summonerName; //visible to other players
    protected String password;
    protected String muteMsg;
    protected String apiKey;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("League Muter");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        //load old field entries
        Map<String, String> prefsMap = getPreferences();
        //
        Label userName = new Label("login username:");
        grid.add(userName, 0, 1);
        
        TextField userTextField = new TextField();
        userTextField.setText(prefsMap.get("username"));
        grid.add(userTextField, 1, 1);

        Label summonerName = new Label("summoner name:");
        grid.add(summonerName, 2, 1);

        TextField summonerNameTextField = new TextField();
        summonerNameTextField.setText(prefsMap.get("summonerName"));
        grid.add(summonerNameTextField, 3, 1);

        Label pw = new Label("password:");
        grid.add(pw, 0, 2);

        PasswordField passTextField = new PasswordField();
        grid.add(passTextField, 1, 2);

        Label muteMessage = new Label("Mute Message:");
        grid.add(muteMessage, 0, 3);

        TextField muteMsgTextField = new TextField();
        muteMsgTextField.setText(prefsMap.get("muteMsg"));
        grid.add(muteMsgTextField, 1, 3);


        Label apiKey = new Label("API Key:");
        grid.add(apiKey, 0, 4);

        TextField apiKeyTextField = new TextField();
        apiKeyTextField.setText(prefsMap.get("apiKey"));
        grid.add(apiKeyTextField, 1, 4);

        Button btn = new Button("press me to mute once your game is done loading");
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

                GUI.this.username = userTextField.getText(); //used to log in
                GUI.this.summonerName = summonerNameTextField.getText(); //visible to other players
                GUI.this.password = passTextField.getText();
                GUI.this.muteMsg = muteMsgTextField.getText();
                GUI.this.apiKey = apiKeyTextField.getText();
                setPreferences(GUI.this.username, GUI.this.summonerName, GUI.this.muteMsg, GUI.this.apiKey);

                ChatUser chatUser = new ChatUser(GUI.this.username, GUI.this.password, GUI.this.apiKey);
                List<User> friends = chatUser.getFriends();
                
                APIuser apiUser = new APIuser(GUI.this.summonerName, GUI.this.apiKey);
                List<User> inGames = apiUser.getInGame();

                List<User> toMute = new ArrayList<>();
                for (User user: inGames) {
                    if (!friends.contains(user) && !user.getName().toLowerCase().equals(GUI.this.username.toLowerCase())) {
                        toMute.add(user);
                    }
                }
                RobotMuter robotMuter = new RobotMuter();
                
                //give user some time to tab back in
                final int SLEEP_TIME = 6000;
                try {
                    Thread.currentThread().sleep(SLEEP_TIME);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                
                //type message, mute everyone
                try {
                    robotMuter.typeString(GUI.this.muteMsg);
                } catch (AWTException ae) {
                    System.out.println(ae.getMessage());
                }
                for (User mutee: toMute) {
                    try {
                        robotMuter.mute(mutee);
                    } catch (AWTException ae) {
                        System.out.println(ae.getMessage());
                    }
                }
            }
        });

        Scene scene = new Scene(grid, 900, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void setPreferences(String username, String summonerName, String muteMsg, String apiKey) {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        prefs.put("username", username);
        prefs.put("summonerName", summonerName);
        prefs.put("muteMsg", muteMsg);
        prefs.put("apiKey", apiKey);
    }

    private Map<String, String> getPreferences() {
        Map<String, String> prefMap = new HashMap<>();
        prefs = Preferences.userRoot().node(this.getClass().getName());
        prefMap.put("username", prefs.get("username", ""));
        prefMap.put("summonerName", prefs.get("summonerName", ""));
        prefMap.put("muteMsg", prefs.get("muteMsg", ""));
        prefMap.put("apiKey", prefs.get("apiKey", ""));
        return prefMap;
    }
}
