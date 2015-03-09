package chatbot.services;

import chatbot.commands.base.AbstractCommand;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by matthias on 02.03.2015.
 */
@Component
public class CommandService {
/*
    final Set<AbstractCommand> commands;

    @Autowired
    public CommandService(Set<AbstractCommand> commands) {
        this.commands = commands;
    }

    List<String> getCommandNames(){
        List<String> commandNames = Lists.newArrayList();
        for(AbstractCommand command : commands){
            commandNames.addAll(command.getCommands());
        }
        return commandNames;
    }
    */
}
