package chatbot.orchestration;

import chatbot.repositories.utils.PersistenceUtils;
import chatbot.services.ViewerFactory;
import chatbot.services.ViewerService;
import org.pircbotx.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * Created by matthias on 09.03.2015.
 */
@Component
public class BotInitializationOrchestrationService {

    private final ViewerService viewerService;
    private final PersistenceUtils persistenceUtils;

    @Autowired
    public BotInitializationOrchestrationService(ViewerService viewerService, PersistenceUtils persistenceUtils) {
        this.viewerService = viewerService;
        this.persistenceUtils = persistenceUtils;
    }

    public void initializeViewers(Set<User> currentViewers) {
        EntityManager em = persistenceUtils.startTransaction();
        viewerService.setAllViewersToOffline(em);
        viewerService.setViewersToOnline(ViewerFactory.createViewers(em, currentViewers));
        persistenceUtils.commitTransactionAndCloseEM(em);
    }
}
