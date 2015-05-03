import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import java.util.Map;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import javax.net.ssl.HttpsURLConnection;

public class APIuser {
    private String apiKey;
    private String summonerName;
    private String nameToIdURL = "https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/";
    private String currentGameURL = "https://na.api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/NA1/"
    public APIuser(String summonerName, String apiKey) {
        this.apiKey = apiKey;
        this.summonerName = summonerName;
    }
    public List<User> getInGame() {
        //get id of user of our program
        playerID = getIdFromName(summonerName);
        
        //get data for game the user is currently playing
        String urlString = currentGameURL + playerID + 
        return null; 
    }
    public String getIdFromName(String name) {
        StringBuffer response;
        try {
            String urlString = nameToIdURL + summonerName + "?api_key=" + apiKey;
            System.out.println(urlString);
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
     
            // optional default is GET
            con.setRequestMethod("GET");
     
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
     
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
     
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
     
             
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject) parser.parse(response.toString());
            return String.valueOf((((JSONObject) (jObject.get(summonerName.toLowerCase()))).get("id")));
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
