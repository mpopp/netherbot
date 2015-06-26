package chatbot.datacollectors.points;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Data collector summing up streampoints for the current session
 * Created by matthias.popp on 04.02.2015.
 */
@Component
public class StreampointsCollector implements Runnable {
    private static final long SLEEP_INTERVAL = 2000; //new points every 15 seconds;
    private static final int PERSISTENCE_INTERVAL = 5; //defines how often the wallet should be written to db.


    private final ViewerRepository viewerRepository;

    private final PropertyFileService propertyFileService;


    private Boolean run;

    @Autowired
    public StreampointsCollector(ViewerRepository viewerRepository, PropertyFileService propertyFileService) {
        this.propertyFileService = propertyFileService;
        this.viewerRepository = viewerRepository;


        this.run = false;
    }

    public void stop() {
        run = false;
    }

    @Override
    public void run() {
        int persistenceCount = 0; //persist every n iterations
        run = true;

        while (run) {
            //TODO move that code into an orchestration service.
            Set<Viewer> currentViewers = viewerRepository.findCurrentViewers();
            for (Viewer viewer : currentViewers) {
                if (!reloadBlacklist().contains(viewer)) {
                    Long nrOfTicketsToAdd = getNrOfTicketsToAdd(viewer);
                    viewer.wallet.sessionPoints += nrOfTicketsToAdd;
                    viewer.wallet.totalPoints += nrOfTicketsToAdd;
                }
            }
            persistenceCount++;
            if (persistenceCount % PERSISTENCE_INTERVAL == 0) {
                persistenceCount = 0;
                viewerRepository.saveViewers( currentViewers);
            }
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private Set<Viewer> reloadBlacklist() {
        Set<Viewer> blacklist = new HashSet<Viewer>();
        try {
            final Properties properties = propertyFileService.loadPropertiesFile(Propertyfiles.BLACKLIST);
            final Set<String> blacklistedNicks = propertyFileService.loadAllPropertyKeysFromFileByValue(properties, "points");
            for (String blacklistedNick : blacklistedNicks) {
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

    private Long getNrOfTicketsToAdd(Viewer viewer) {
        return 1L;
    }
}
