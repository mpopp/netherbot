package chatbot.datacollectors.points;

import chatbot.datacollectors.DataCollector;
import chatbot.entities.Viewer;
import chatbot.orchestration.StreamPointsOrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Data collector summing up streampoints for the current session
 * Created by matthias.popp on 04.02.2015.
 */
@Component
public class StreampointsCollector implements Runnable, DataCollector {
    private static final long SLEEP_INTERVAL = 2000; //new points every 15 seconds;
    private static final int PERSISTENCE_INTERVAL = 5; //defines how often the wallet should be written to db.

    private StreamPointsOrchestrationService streamPointsOrchestrationService;
    private Boolean run;

    @Autowired
    public StreampointsCollector(StreamPointsOrchestrationService streamPointsOrchestrationService) {
        this.streamPointsOrchestrationService = streamPointsOrchestrationService;
        this.run = false;
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
        int persistenceCount = 0; //persist every n iterations
        while (run) {
            Set<Viewer> currentViewers = streamPointsOrchestrationService.incrementPointsForCurrentViewers();
            persistenceCount++;
            persistenceCount = persistViewerPoints(persistenceCount, currentViewers);
            sleepAWhile();
        }

    }

    private void sleepAWhile() {
        try {
            Thread.sleep(SLEEP_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int persistViewerPoints(int persistenceCount, Set<Viewer> currentViewers) {
        if (persistenceCount % PERSISTENCE_INTERVAL == 0) {
            persistenceCount = 0;
            streamPointsOrchestrationService.persistPointsForViewers(currentViewers);
        }
        return persistenceCount;
    }
}
