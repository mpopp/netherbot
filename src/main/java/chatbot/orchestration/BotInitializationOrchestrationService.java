package chatbot.orchestration;


import chatbot.core.MongoDbConfiguration;
import chatbot.services.ViewerService;
import org.mongodb.morphia.Datastore;
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
    private final Datastore datastore;

    @Autowired
    public BotInitializationOrchestrationService(ViewerService viewerService, MongoDbConfiguration configuration) {
        this.viewerService = viewerService;
        datastore = configuration.getDatastore();
    }

    public void initializeViewers(Set<User> currentViewers) {
        viewerService.setAllViewersToOffline(datastore);
        for (User u : currentViewers) {
            viewerService.setViewerToOnlineOrCreateIfNotExisting(datastore, u.getNick());
        }
    }
}
