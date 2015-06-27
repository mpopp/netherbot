package chatbot.datacollectors.points;

import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;
import chatbot.services.LevelCurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This strategy returns points based on the state of a Viewer. States are Viewer, Follower, Subscriber
 */
@Component
public class ViewerStateStrategy implements PointIncrementStrategy {


    private final LevelCurveService levelCurveService;

    @Autowired
    public ViewerStateStrategy(final LevelCurveService levelCurveService){

        this.levelCurveService = levelCurveService;
    }


    @Override
    public PointIncrement calculatePointIncrement(Viewer viewer) {
        long totalPointIncrement = calculateTotalPointIncrement(viewer);
        long viewerLevel = levelCurveService.calculateLevel(viewer);
        long sessionPointIncrement = viewerLevel <= 1
                ? totalPointIncrement
                : calculateSessionPointIncrement(viewer, totalPointIncrement);
        return new PointIncrement(sessionPointIncrement, totalPointIncrement);
    }

    /**
     * Viewers should get  +1 point every 5 levels .. we always round up
     *
     * @param viewer to calculate the icnrement for
     * @param totalPointIncrement to take as a base for the increment calculation
     * @return the session point increment value
     */
    private long calculateSessionPointIncrement(Viewer viewer, long totalPointIncrement) {
        Double incrementBoost = new Double(Math.ceil(levelCurveService.calculateLevel(viewer) / 5.0));
        return totalPointIncrement + incrementBoost.longValue();
    }

    private long calculateTotalPointIncrement(Viewer viewer) {
        //TODO: check for state instead of simply assigning all the viewers the same amount for total point increment.
        return 1;
    }
}
