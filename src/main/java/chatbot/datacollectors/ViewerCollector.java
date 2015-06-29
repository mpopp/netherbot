package chatbot.datacollectors;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.ircbot.BotWrapper;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserChannelDao;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerCollector implements DataCollector, Runnable {

    private final ViewerService viewerService;
    private final UserRolesService userRoleService;
    private BotWrapper botWrapper;
    private boolean run;


    @Autowired
    public ViewerCollector(ViewerService viewerService, UserRolesService userRoleService, BotWrapper botWrapper) {
        this.viewerService = viewerService;
        this.userRoleService = userRoleService;
        this.botWrapper = botWrapper;
    }

    public void onJoin(JoinEvent event) throws Exception {
        viewerService.setViewerToOnlineOrCreateIfNotExisting(event.getUser().getNick());
    }

    public void onPart(PartEvent event) throws Exception {
        viewerService.setViewerToOffline(event.getUser().getNick());
    }

    @Override
    public void start() {
        run = true;
        new Thread(this).start();
    }

    @Override
    public void stop() {
        run = false;
    }


    @Override
    public void run() {
        PircBotX bot = null;
        while(bot == null){
            bot = botWrapper.getBot();
        }

        while(run){
            UserChannelDao<User, Channel> dao = bot.getUserChannelDao();
            ImmutableSortedSet<User> allUsers = dao.getAllUsers();
            //TODO compare viewers from last iteration with allUsers to find out who went online and who went offline
            //TODO save set of viewers for comparison in next iteration
            //TODO transform filtered users (went offline and went online) to viewers
            //TODO perform database operations for the filtered users
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
