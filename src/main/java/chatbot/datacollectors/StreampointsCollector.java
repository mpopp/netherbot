package chatbot.datacollectors;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Data collector summing up streampoints for the current session
 * Created by matthias.popp on 04.02.2015.
 */
@Component
public class StreampointsCollector implements Runnable {
    private static final long SLEEP_INTERVAL = 15000; //new points every 15 seconds;


    /**
     * Map to be used for collecting the tickets per user for the current raffle.
     */
    private Map<Viewer, Long> sessionpoints;

    private final ViewerRepository viewerRepository;

    private final PropertyFileService propertyFileService;

    private Boolean run;

    @Autowired
    public StreampointsCollector(ViewerRepository viewerRepository, PropertyFileService propertyFileService){
        this.propertyFileService = propertyFileService;
        this.viewerRepository = viewerRepository;
        this.sessionpoints = new HashMap<Viewer, Long>();
        this.run = false;
    }

    private Set<Viewer> reloadBlacklist() {
        Set<Viewer> blacklist = new HashSet<Viewer>();
        try {
            final Properties properties = propertyFileService.loadPropertiesFile(Propertyfiles.BLACKLIST);
            final Set<String> blacklistedNicks = propertyFileService.loadAllPropertyKeysFromFileByValue(properties, "raffle");
            for(String blacklistedNick : blacklistedNicks) {
                Viewer v = new Viewer();
                v.nick = blacklistedNick;
                blacklist.add(v);
            }
            return blacklist;
        } catch (IOException e) {
            System.err.println("Something went wrong when trying to load the blacklist for raffles. Continuing with " +
                    "empty blacklist!");
        }
        return blacklist;
    }

    public void stop() {
        final Set<Viewer> currentViewers = viewerRepository.findCurrentViewers();
        raffleUsers = Maps.filterKeys(sessionpoints, new Predicate<Viewer>() {
            @Override
            public boolean apply(Viewer input) {
                return currentViewers.contains(input);
            }
        });
        run = false;
    }

    public void reset() throws IOException {
        sessionpoints = raffleService.refreshRaffle();
    }

    public void start() throws IOException {
        sessionpoints = raffleService.loadRaffle();
        reloadBlacklist();
        raffleUsers = null;
        run = true;
    }

    @Override
    public void run() {
        int persistenceCount = 0; //persist every n iterations
        while (run) {
            for (Viewer viewer: viewerRepository.findCurrentViewers()) {
                if(!reloadBlacklist().contains(viewer)) {
                    Long nrOfTicketsToAdd = getNrOfTicketsToAdd(viewer);
                    viewer.wallet.sessionPoints += nrOfTicketsToAdd;
                    viewer.wallet.totalPoints += nrOfTicketsToAdd;
                }
            }
            try {
                Thread.sleep(SLEEP_INTERVAL);
                if(persistenceCount % 10 == 0){
                    persistenceCount = 0;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Long getNrOfTicketsToAdd(Viewer nick) {
        return 1L;
    }
}
