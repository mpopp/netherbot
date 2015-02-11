package chatbot.commands.base;

import chatbot.core.PatternConstants;
import chatbot.repositories.api.MessageRespondRepository;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 28.11.13
 * Time: 19:48
 * This command just reads the patterns and assigned messages from a property file. This command is meant for just
 * sending messages and is designed that generic to provide the possibility of adding new message response commands at
 * bot runtime via a separate add command.
 */
@Component
public class SimpleMessageRespondCommand extends ListenerAdapter {

    @Autowired
    private MessageRespondRepository repository;

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if (event.getMessage().startsWith(PatternConstants.PREFIX)) {
            String message =  repository.findMessageForCommand(event.getMessage());
            for (User user : event.getBot().getUserChannelDao().getAllUsers()) {
                System.out.println(user.getNick());
            }
            ;
            if(!StringUtils.isBlank(message)){
                event.respond(message);
            }
        }
    }

}
