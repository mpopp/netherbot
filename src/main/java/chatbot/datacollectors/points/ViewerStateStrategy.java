package chatbot.datacollectors.points;

import chatbot.dto.PointIncrement;
import chatbot.entities.Viewer;
import chatbot.services.LevelCurveService;
import chatbot.services.TwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This strategy returns points based on the state of a Viewer. States are Viewer, Follower, Subscriber
 */
//TODO remove level calculation from here. Move it to StreamPointsCollector
@Component
public class ViewerStateStrategy implements PointIncrementStrategy {


    private final LevelCurveService levelCurveService;
    private final TwitchService twitchService;

    @Autowired
    public ViewerStateStrategy(final LevelCurveService levelCurveService, final TwitchService twitchService){
        this.levelCurveService = levelCurveService;
        this.twitchService = twitchService;
    }


    @Override
    public PointIncrement calculatePointIncrement(Viewer viewer) {
        viewer.setLevel(levelCurveService.calculateLevel(viewer));
        long totalPointIncrement = calculateTotalPointIncrement(viewer);
        long sessionPointIncrement = viewer.getLevel() <= 1
                ? totalPointIncrement
                : calculateSessionPointIncrement(viewer, totalPointIncrement);
        return new PointIncrement(sessionPointIncrement, totalPointIncrement);
    }

    /**
     * Viewers should get  +1 point every 5 levels .. we always round up
     *
     * @param viewer to calculate the increment for
     * @param totalPointIncrement to take as a base for the increment calculation
     * @return the session point increment value
     */
    private long calculateSessionPointIncrement(Viewer viewer, long totalPointIncrement) {
        Double incrementBoost = new Double(Math.ceil(viewer.getLevel() / 5.0));
        return totalPointIncrement + incrementBoost.longValue();
    }

    /*
    * lurkers -> 1p
    * follower -> 2p
    * subscriber -> 5p
    * notifications enabled -> +1p
    * */
    private long calculateTotalPointIncrement(Viewer viewer) {

        long points = viewer.hasSubscribed() ? 5
                : viewer.isFollowing() ? 2
                : 1;
        return viewer.hasNotificationsEnabled() ? points + 1 : points;
    }
}
