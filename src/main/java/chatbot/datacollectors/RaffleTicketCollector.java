package chatbot.datacollectors;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import chatbot.services.RaffleService;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by matthias.popp on 04.02.2015.
 */
@Component
public class RaffleTicketCollector implements Runnable {
    private static final long SLEEP_INTERVAL = 2000; //60000;


    /**
     * Map to be used for collecting the tickets per user for the current raffle.
     */
    private Map<Viewer, Long> usertickets;

    /**
     * Map to be returned by getUsers after the raffle has stopped;
     */
    private Map<Viewer, Long> raffleUsers;

    @Autowired
    private RaffleService raffleService;

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private PropertyFileService propertyFileService;

    private Boolean run;

    public RaffleTicketCollector(){
        this.usertickets = new HashMap<Viewer, Long>();
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
        raffleUsers = Maps.filterKeys(usertickets, new Predicate<Viewer>() {
            @Override
            public boolean apply(Viewer input) {
                return currentViewers.contains(input);
            }
        });
        run = false;
    }

    public void reset() throws IOException {
        usertickets = raffleService.refreshRaffle();
    }

    public void start() throws IOException {
        usertickets = raffleService.loadRaffle();
        reloadBlacklist();
        raffleUsers = null;
        run = true;
    }

    public Map<Viewer, Long> getUsers(){
        return raffleUsers;
    }

    @Override
    public void run() {
        while (run) {
            for (Viewer viewer: viewerRepository.findCurrentViewers()) {
                if(!reloadBlacklist().contains(viewer)) {
                    Long nrOfTicketsToAdd = getNrOfTicketsToAdd(viewer);
                    raffleService.addTickets(usertickets, viewer, nrOfTicketsToAdd);
                } else {
                    raffleService.removeUser(usertickets, viewer);
                }
            }
            raffleService.saveRaffle(usertickets);
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Collecting tickets has stopped");
    }

    private Long getNrOfTicketsToAdd(Viewer nick) {
        return 1L;
    }
}
