package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerCollector extends AbstractCommand {

    @Autowired
    private ViewerService viewerService;

    @Autowired
    private UserRolesService userRoleService;

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        viewerService.setViewerToOnlineOrCreateIfNotExisting(event.getUser().getNick());
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        viewerService.setViewerToOffline(event.getUser().getNick());
    }

    @Override
    protected void executeCommand(MessageEvent event) {

    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        return userRoleService.isUserOperatorInChannel(event.getUser(), event.getChannel());
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return message.equals(PatternConstants.PREFIX + "refreshviewerlist");
    }
}
