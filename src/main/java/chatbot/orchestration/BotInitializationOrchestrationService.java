package chatbot.orchestration;


import chatbot.core.MongoDbConfiguration;
import chatbot.services.ViewerService;
import com.mongodb.client.MongoDatabase;
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
    private final MongoDatabase mongoDatabase;

    @Autowired
    public BotInitializationOrchestrationService(ViewerService viewerService, MongoDbConfiguration configuration) {
        this.viewerService = viewerService;
        mongoDatabase = configuration.getMongoDatabase();
    }

    public void initializeViewers(Set<User> currentViewers) {
        viewerService.setAllViewersToOffline(mongoDatabase);
        for (User u : currentViewers) {
            viewerService.setViewerToOnlineOrCreateIfNotExisting(mongoDatabase, u.getNick());
        }
    }
}
