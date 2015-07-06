package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerCollector extends AbstractCommand {

    private final ViewerService viewerService;
    private final UserRolesService userRoleService;


    @Autowired
    public ViewerCollector(ViewerService viewerService, UserRolesService userRoleService) {
        this.viewerService = viewerService;
        this.userRoleService = userRoleService;
    }

    //TODO: REMOVE SYSOUTS AFTER PROBLEM IS FIXED
    @Override
    public void onJoin(JoinEvent event) throws Exception {
        System.out.println("#### Player joined: " + event.getUser().getNick());
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
