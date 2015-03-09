package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Component
public class ViewerService {

    @Autowired
    private ViewerRepository viewerRepo;

    public void setViewerToOnlineOrCreateIfNotExisting(String nick){
        if(viewerRepo.isViewerExisting(nick)){
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
     * @param nick The nick to change the watching state for.
     */
    public void setViewerToOffline(String nick){
        viewerRepo.updateWatchingState(nick, false);
    }

    public void setAllViewersToOffline(EntityManager em) {
        viewerRepo.updateWatchingStateForAllUsers(false);
    }

    public void setViewersToOnline(Set<Viewer> viewers) {
        for(Viewer v : viewers){
            viewerRepo.updateWatchingState(v.nick, true);
        }
    }
}
