import com.github.theholywaffle.lolchatapi.ChatServer;
import com.github.theholywaffle.lolchatapi.FriendRequestPolicy;
import com.github.theholywaffle.lolchatapi.LolChat;
import com.github.theholywaffle.lolchatapi.listeners.ChatListener;
import com.github.theholywaffle.lolchatapi.riotapi.RateLimit;
import com.github.theholywaffle.lolchatapi.riotapi.RiotApiKey;
import com.github.theholywaffle.lolchatapi.wrapper.Friend;
import java.util.List;
import java.util.ArrayList;

public class ChatUser {
    private String username;
    private String password;
    private String apiKey;

    public ChatUser(String username, String password, String apiKey) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey; 
    }
    public List<User> getFriends() {
        final LolChat api = new LolChat(ChatServer.NA2,
                FriendRequestPolicy.ACCEPT_ALL, new RiotApiKey(apiKey,
                        RateLimit.DEFAULT));

        List<User> friendList = new ArrayList<>();
        if (api.login(username, password)) {
            for (final Friend f : api.getFriends()) {
                User friend = new User(f.getName());
                friendList.add(friend);
            }
        }
        return friendList;
    }
}
