package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
//TODO refactor -> this is a command, not a datacollector!
public class ViewerCollector extends ListenerAdapter {

    private final ViewerService viewerService;


    @Autowired
    public ViewerCollector(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        viewerService.setViewerToOnlineOrCreateIfNotExisting(event.getUser().getNick());
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        viewerService.setViewerToOffline(event.getUser().getNick());
    }
}
