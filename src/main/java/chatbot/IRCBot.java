package chatbot;

import chatbot.datacollectors.DataCollectors;
import chatbot.ircbot.BotWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 28.11.13
 * Time: 07:31
 * To change this template use File | Settings | File Templates.
 */
@ComponentScan
@Configuration
@EnableScheduling
public class IRCBot {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(IRCBot.class);
        BotWrapper bot = context.getBean(BotWrapper.class);
        DataCollectors collectors = context.getBean(DataCollectors.class);
        collectors.start();
        bot.start();
    }
}
