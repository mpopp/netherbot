package chatbot.core;

import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import org.pircbotx.Configuration;
import org.pircbotx.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
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

    private Configuration c;

    PropertyFileService propertyFileService;

    @Autowired
    public ChatBotConfiguration(Set<ListenerAdapter> commands, PropertyFileService propertyFileService) {
        this.propertyFileService = propertyFileService;
        try {
            Properties prop = getConfigurationProperties();
        Configuration.Builder builder = new org.pircbotx.Configuration.Builder()
                .setName(prop.getProperty("bot-name"))
                .setServerHostname(prop.getProperty("server-host"))
                .setServerPort(Integer.parseInt(prop.getProperty("server-port")))
                .setWebIrcUsername(prop.getProperty("irc-username"))
                .setServerPassword(prop.getProperty("oauth-key"))
                .addAutoJoinChannel(prop.getProperty("channel-to-join"))
                .setAutoReconnect(Boolean.parseBoolean(prop.getProperty("auto-reconnect")));

        for (ListenerAdapter command : commands) {
            builder.addListener(command);
        }
            c = builder.buildConfiguration();
        } catch (IOException e) {
            c = null;
        }

    }

    private Properties getConfigurationProperties() throws IOException {
        return propertyFileService.loadPropertiesFile(Propertyfiles.BOT_CONNECTION_PARAMETERS);
    }

    public Configuration getConfiguration() {
        return c;
    }
}
