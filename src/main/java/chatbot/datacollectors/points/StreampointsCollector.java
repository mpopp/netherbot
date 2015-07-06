package chatbot.datacollectors.points;

import chatbot.datacollectors.DataCollector;
import chatbot.entities.Viewer;
import chatbot.orchestration.StreamPointsOrchestrationService;
import chatbot.repositories.api.ViewerRepository;
import chatbot.services.LevelCurveService;
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
    private LevelCurveService levelCurveService;
    private final ViewerRepository viewerRepository;

    @Autowired
    public StreampointsCollector(StreamPointsOrchestrationService streamPointsOrchestrationService, LevelCurveService levelCurveService, ViewerRepository viewerRepository) {
        this.streamPointsOrchestrationService = streamPointsOrchestrationService;
        this.levelCurveService = levelCurveService;
        this.viewerRepository = viewerRepository;
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
            Set<Viewer> currentViewers = viewerRepository.findCurrentViewers();
            levelCurveService.adjustLevels(currentViewers);
            streamPointsOrchestrationService.incrementPointsForCurrentViewers(currentViewers);
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
