package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerCollector extends AbstractCommand {

    private final ViewerService viewerService;
    private final UserRolesService userRoleService;
    private final PersistenceUtils persistenceUtils;

    @Autowired
    public ViewerCollector (ViewerService viewerService, UserRolesService userRoleService, PersistenceUtils persistenceUtils){
        this.viewerService = viewerService;
        this.userRoleService = userRoleService;
        this.persistenceUtils = persistenceUtils;
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        EntityManager em = persistenceUtils.startTransaction();
        viewerService.setViewerToOnlineOrCreateIfNotExisting(em, event.getUser().getNick());
        persistenceUtils.commitTransactionAndCloseEM(em);
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        EntityManager em = persistenceUtils.startTransaction();
        viewerService.setViewerToOffline(em, event.getUser().getNick());
        persistenceUtils.commitTransactionAndCloseEM(em);
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
