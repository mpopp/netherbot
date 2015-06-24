package chatbot.datacollectors.points;

import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

/**
 * This strategy returns points based on the state of a Viewer. States are Viewer, Follower, Subscriber
 */
@Component
public class ViewerStateStrategie implements PointIncrementStrategy {


    @Override
    public PointIncrement calculatePointIncrement(Viewer viewer) {
        //TODO: check for state instead of simply assigning all the viewers the same amount for total point increment.
        long totalPointIncrement = calculateTotalPointIncrement(viewer);
        long sessionPointIncrement = viewer.level <= 1 ? 1 : calculateSessionPointIncrement(viewer, totalPointIncrement);
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
        Double incrementBoost = new Double(Math.ceil(viewer.level / 5.0));
        return totalPointIncrement + incrementBoost.longValue();
    }

    private long calculateTotalPointIncrement(Viewer viewer) {
        return 1;
    }
}
