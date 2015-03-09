package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Set;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Component
public class ViewerService {

    private ViewerRepository viewerRepo;

    @Autowired
    public ViewerService(final ViewerRepository viewerRepo){
        this.viewerRepo = viewerRepo;
    }

    public Viewer findViewerByNick(EntityManager em, String nick){
        return viewerRepo.findViewerByNick(em, nick);
    }

    public void setViewerToOnlineOrCreateIfNotExisting(EntityManager em, String nick){
        if(viewerRepo.isViewerExisting(em, nick)){
            viewerRepo.updateWatchingState(em, nick, true);
        } else {
            Viewer v = new Viewer();
            v.nick = nick;
            v.watching = true;
            viewerRepo.saveViewer(em, v);
        }
    }

    /**
     * Sets the viewer to offline. At this state we assume the user was already added to the database before.
     * @param nick The nick to change the watching state for.
     */
    public void setViewerToOffline(EntityManager em, String nick) {
        viewerRepo.updateWatchingState(em, nick, false);
    }

    public void setAllViewersToOffline(EntityManager em) {
        viewerRepo.updateWatchingStateForAllUsers(em, false);
    }
}
