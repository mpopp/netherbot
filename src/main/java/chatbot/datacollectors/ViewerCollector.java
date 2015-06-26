package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.MongoDbConfiguration;
import chatbot.core.PatternConstants;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import org.mongodb.morphia.Datastore;
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
    private final Datastore datastore;


    @Autowired
    public ViewerCollector(ViewerService viewerService, UserRolesService userRoleService, MongoDbConfiguration configuration) {
        this.viewerService = viewerService;
        this.userRoleService = userRoleService;
        datastore = configuration.getDatastore();
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        viewerService.setViewerToOnlineOrCreateIfNotExisting(datastore, event.getUser().getNick());
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        viewerService.setViewerToOffline(datastore, event.getUser().getNick());
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
