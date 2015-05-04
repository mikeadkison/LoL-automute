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
    @Override
    public int hashCode() {
        return 41 * (41 + name.hashCode());
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User theOther = (User) other;
        return this.getName().equals(theOther.getName());
    }
}
