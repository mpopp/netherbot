package chatbot.datacollectors.channel;

import chatbot.datacollectors.DataCollector;
import chatbot.services.TwitchService;
import chatbot.services.ViewerService;
import org.springframework.stereotype.Component;

/**
 * @author Matthias Popp
 */
@Component
public class GoLiveCollector implements Runnable, DataCollector {
    boolean run;
    private TwitchService twitchService;
    private ViewerService viewerService;
    boolean wasLivePreviously;

    public GoLiveCollector(TwitchService twitchService, ViewerService viewerService) {
        this.twitchService = twitchService;
        this.viewerService = viewerService;
        wasLivePreviously = false;
    }

    @Override
    public void start() {
        run = true;
    }

    @Override
    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        while (run) {
            boolean isLive = twitchService.isLive("gamingdaddies");
            //We want to reset all session points whenever gamingdaddies goes online or offline.
            if (isLive != wasLivePreviously) {
                viewerService.resetAllSessionPoints();
            }
            wasLivePreviously = isLive;
            sleepFor5Minutes();
        }
    }

    private void sleepFor5Minutes() {
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
