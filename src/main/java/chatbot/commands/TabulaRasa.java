package chatbot.commands;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.repositories.api.ViewerRepository;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias on 08.07.2015.
 */
@Component
public class TabulaRasa extends AbstractCommand {

    private final ViewerRepository repository;

    @Autowired
    public TabulaRasa(final ViewerRepository repository){
        this.repository = repository;
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        repository.deleteAll();
        event.respond("Tabula Rasa. You can start over again now.");
    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        String nick = event.getUser().getNick().toLowerCase();
        return nick.equals("netherbrain") || nick.equals("kendoros");
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return message.equals(PatternConstants.PREFIX + "tabula rasa");
    }
}
