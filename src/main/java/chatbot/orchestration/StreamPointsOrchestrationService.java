package chatbot.orchestration;

import chatbot.datacollectors.points.PointIncrementStrategy;
import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ConcurrentModificationException;
import java.util.Set;

/**
 * @author Matthias Popp
 */
@Component
public class StreamPointsOrchestrationService {

    private final ViewerRepository viewerRepository;
    private final PointIncrementStrategy pointIncrementStrategy;

    @Autowired
    public StreamPointsOrchestrationService(ViewerRepository viewerRepository, PointIncrementStrategy pointIncrementStrategy) {
        this.viewerRepository = viewerRepository;
        this.pointIncrementStrategy = pointIncrementStrategy;
    }

    public Set<Viewer> incrementPointsForCurrentViewers(Set<Viewer> currentViewers) {
        for (Viewer viewer : currentViewers) {
            viewer.collectPoints(pointIncrementStrategy.calculatePointIncrement(viewer));
        }
        return currentViewers;
    }

    /**
     * This persist operation must not overwrite a viewer in case of concurrent modification, ever. We can live with a
     * little inconsistency in the viewer Wallet, but it would be a tragedy if e.g. a viewers points were reset and we
     * just overwrite it with a huge amount of points in the ConcurrentModification handling.
     * @param viewers The viewers to save.
     */
    public void persistPointsForViewers(Set<Viewer> viewers) {
        try {
            viewerRepository.saveViewers(viewers);
        } catch(ConcurrentModificationException e){
        }
    }
}
