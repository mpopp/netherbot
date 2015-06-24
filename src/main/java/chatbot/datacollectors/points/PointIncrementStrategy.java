package chatbot.datacollectors.points;

import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;

/**
 * Interface for point increment strategies. This type of strategy defines how many points a viewer will gain per
 * time frame.
 */
public interface PointIncrementStrategy {

    PointIncrement calculatePointIncrement(final Viewer viewer);
}
