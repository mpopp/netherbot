package chatbot.ircbot;

import chatbot.core.ChatBotConfiguration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 30.11.13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BotWrapper {

    @Autowired
    private ChatBotConfiguration configuration;

    private PircBotX bot;

    public void start() {
        bot = new PircBotX(configuration.getConfiguration());

        try {
            bot.startBot();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IrcException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
