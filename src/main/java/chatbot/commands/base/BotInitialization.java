package chatbot.commands.base;

import chatbot.entities.Viewer;
import chatbot.orchestration.BotInitializationOrchestrationService;
import chatbot.factories.ViewerFactory;
import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by matthias on 04.03.2015.
 */
@Component
public class BotInitialization extends ListenerAdapter {

    private final BotInitializationOrchestrationService service;
    private final ViewerFactory viewerFactory;

    @Autowired
    public BotInitialization(final BotInitializationOrchestrationService service, final ViewerFactory viewerFactory) {
        this.service = service;
        this.viewerFactory = viewerFactory;
    }


    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        ImmutableSortedSet<User> currentViewers = event.getBot().getUserChannelDao().getAllUsers();
        Set<Viewer> viewers = viewerFactory.createViewers(currentViewers);
        service.initializeViewers(viewers);
    }
}
