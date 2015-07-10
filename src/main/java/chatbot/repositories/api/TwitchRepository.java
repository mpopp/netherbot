package chatbot.repositories.api;

import chatbot.dto.twitch.Followers;

import java.util.Set;

/**
 * @author Matthias Popp
 */
public interface TwitchRepository {

    public String findFollowersForChannel(String channelName);
    public String findStream(String channelName);
}
