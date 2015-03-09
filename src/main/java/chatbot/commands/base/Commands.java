package chatbot.commands.base;

import chatbot.core.PatternConstants;
import chatbot.services.CommandService;
import chatbot.services.UserRolesService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by matthias on 02.03.2015.
 */
@Component
public class Commands extends AbstractCommand{

    private final UserRolesService userRolesService;

    /**
     * Late binding because command service needs all commands for executing the comamnd specific methods.
     */
    @Autowired
    private CommandService commandService;

    @Autowired
    public Commands(UserRolesService userRolesService){
        this.userRolesService = userRolesService;
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        if(userRolesService.isUserOperatorInChannel(event.getUser(), event.getChannel())){
            event.respond("");
        } else {
            event.respond("");
        }
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return StringUtils.equalsIgnoreCase(PatternConstants.PREFIX + "commands", message);
    }

    @Override
    public List<String> getCommands() {
        return Lists.newArrayList();
    }
}
