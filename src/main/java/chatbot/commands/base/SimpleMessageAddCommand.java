package chatbot.commands.base;

import chatbot.core.PatternConstants;
import chatbot.repositories.api.MessageRespondRepository;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 30.11.13
 * Time: 11:48
 * With this command you can add simple message responses for command patterns which are then returned by the
 * SimpleMessageRespondCommand.
 */
@Component
public class SimpleMessageAddCommand extends AbstractConfigurableMessageCommand {

    @Autowired
    private MessageRespondRepository repository;

    @Override
    protected void executeCommand(MessageEvent event) {
        String[] commandTokens = StringUtils.split(event.getMessage(), '|');
        //We compare for a length of 3 because we need something like that !add|commandName|message to respond with.
        if (commandTokens.length == 3) {
            String message = commandTokens[2];
            String command = StringUtils.lowerCase(StringUtils.strip(commandTokens[1]));
            command = StringUtils.startsWith(commandTokens[1], PatternConstants.PREFIX) ? command : PatternConstants.PREFIX + command;
            if(!StringUtils.isBlank(command) ){
            repository.addCommand(command, message);
                event.respond("Added command " + command);
            }
        }
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return StringUtils.startsWithIgnoreCase(StringUtils.strip(message), PatternConstants.PREFIX + "addcommand");
    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event){
        return event.getChannel().getOps().contains(event.getUser());
    }
}
