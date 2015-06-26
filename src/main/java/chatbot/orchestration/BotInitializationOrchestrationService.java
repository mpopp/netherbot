package chatbot.orchestration;


import chatbot.services.ViewerService;
import org.pircbotx.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by matthias on 09.03.2015.
 */
@Component
public class BotInitializationOrchestrationService {

    private final ViewerService viewerService;

    @Autowired
    public BotInitializationOrchestrationService(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    public void initializeViewers(Set<User> currentViewers) {
        viewerService.setAllViewersToOffline();
        for (User u : currentViewers) {
            viewerService.setViewerToOnlineOrCreateIfNotExisting(u.getNick());
        }
    }
}
