public class User {
    private String name;
    private String summonerID;

    public User(String name) {
        this.name = name;
    }
    public User(String name, String summonerID) {
        this.name = name;
        this.summonerID = summonerID;
    }

    public String getName() {
        return name;
    }
    public String getSummonerID() {
        return summonerID;
    }
}
