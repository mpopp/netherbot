package chatbot.commands.base;

import chatbot.orchestration.BotInitializationOrchestrationService;
import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias on 04.03.2015.
 */
@Component
public class BotInitialization extends ListenerAdapter {

    private final BotInitializationOrchestrationService service;

    @Autowired
    public BotInitialization(final BotInitializationOrchestrationService service){
        this.service = service;
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        ImmutableSortedSet<User> currentViewers = event.getBot().getUserChannelDao().getAllUsers();
        service.initializeViewers(currentViewers);
    }
}
