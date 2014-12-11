package chatbot.core;

import org.pircbotx.Configuration;
import org.pircbotx.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 30.11.13
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ChatBotConfiguration {

    private final Configuration c;

    @Autowired
    public ChatBotConfiguration(Set<ListenerAdapter> commands) {
        Configuration.Builder builder = new org.pircbotx.Configuration.Builder()
                .setName("NetherBot")
                .setServerHostname("irc.twitch.tv")
                .setServerPort(6667)
                .setWebIrcUsername("NetherBot")
                .setServerPassword("oauth:3857qt2vq2l7vwg2gitmx4c5nsdx2fc")
                .addAutoJoinChannel("#netherbrain")
                .setAutoReconnect(true);

        for (ListenerAdapter command : commands) {
            builder.addListener(command);
        }

        c = builder.buildConfiguration();
    }

    public Configuration getConfiguration() {
        return c;
    }
}
