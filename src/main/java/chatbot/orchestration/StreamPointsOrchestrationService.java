package chatbot.orchestration;

import chatbot.datacollectors.points.PointIncrementStrategy;
import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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

    public void persistPointsForViewers(Set<Viewer> viewers) {
        viewerRepository.saveViewers(viewers);
    }
}
