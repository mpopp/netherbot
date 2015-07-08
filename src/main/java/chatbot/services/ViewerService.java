package chatbot.services;

import chatbot.dto.twitch.Follower;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Component
public class ViewerService {

    private ViewerRepository viewerRepo;

    @Autowired
    public ViewerService(final ViewerRepository viewerRepo) {
        this.viewerRepo = viewerRepo;
    }

    public Viewer findViewerByNick(String nick) {
        return viewerRepo.findViewerByNick(nick);
    }

    public Set<Viewer> enrichViewersWithFollowerData(Set<Viewer> viewersToEnrich, List<Follower> followers){
        Map<String, Viewer> viewerMap = transformToMap(viewersToEnrich);
        enrichViewersWithFollowerData(followers, viewerMap);
        return Sets.newHashSet(viewerMap.values());
    }

    public void setViewerToOnlineOrCreateIfNotExisting(String nick) {
        if (viewerRepo.isViewerExisting(nick)) {
            viewerRepo.updateWatchingState(nick, true);
        } else {
            Viewer v = new Viewer();
            v.nick = nick;
            v.watching = true;
            viewerRepo.saveViewer(v);
        }
    }

    /**
     * Sets the viewer to offline. At this state we assume the user was already added to the database before.
     *
     * @param nick The nick to change the watching state for.
     */
    public void setViewerToOffline(String nick) {
        viewerRepo.updateWatchingState(nick, false);
    }

    public void setAllViewersToOffline() {
        viewerRepo.updateWatchingStateForAllUsers(false);
    }

    //region HELPER METHODS
    private void enrichViewersWithFollowerData(List<Follower> followers, Map<String, Viewer> viewerMap) {
        for(Follower f : followers){
            Viewer viewer = viewerMap.get(f.getUser().getName().toLowerCase());
            if(viewer != null) {
                viewer.setFollowing(true);
                viewer.setNotificationsEnabled(f.isNotifications());
            }
        }
    }

    private Map<String, Viewer> transformToMap(Set<Viewer> viewersToEnrich) {
        Map<String, Viewer> viewerMap = Maps.newHashMap();
        for(Viewer v : viewersToEnrich){
            viewerMap.put(v.nick.toLowerCase(),v);
        }
        return viewerMap;
    }
    //endregion

}
