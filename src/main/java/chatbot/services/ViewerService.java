package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Component
public class ViewerService {

    private ViewerRepository viewerRepo;
    private PersistenceUtils persistenceUtils;

    @Autowired
    public ViewerService(final ViewerRepository viewerRepo, PersistenceUtils persistenceUtils){
        this.viewerRepo = viewerRepo;
        this.persistenceUtils = persistenceUtils;
    }

    public void setViewerToOnlineOrCreateIfNotExisting(String nick){
        EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        if(viewerRepo.isViewerExisting(em, nick)){
            viewerRepo.updateWatchingState(em, nick, true);
        } else {
            Viewer v = new Viewer();
            v.nick = nick;
            v.watching = true;
            viewerRepo.saveViewer(em, v);
        }
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
    }

    /**
     * Sets the viewer to offline. At this state we assume the user was already added to the database before.
     * @param nick The nick to change the watching state for.
     */
    public void setViewerToOffline(String nick) {
        EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        viewerRepo.updateWatchingState(em, nick, false);
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
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
