package chatbot.services;

import chatbot.dto.twitch.Follower;
import chatbot.dto.twitch.Followers;
import chatbot.repositories.api.TwitchRepository;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Matthias Popp
 */
@Component
public class TwitchService {

    private final TwitchRepository twitchRepository;

    public TwitchService(TwitchRepository twitchRepository){

        this.twitchRepository = twitchRepository;
    }

    public List<Follower> findFollowersForChannel(String channel){
        String responseString = twitchRepository.findFollowersForChannel(channel);
        return responseString == null ? null : Lists.newArrayList(transformToFollowers(responseString).getFollowers());
    }

    protected Followers transformToFollowers(String followersJsonString) {
        Gson gson = new Gson();
        return gson.fromJson(followersJsonString, Followers.class);
    }

}
