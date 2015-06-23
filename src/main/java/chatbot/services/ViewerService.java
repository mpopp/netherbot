package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Viewer findViewerByNick(MongoDatabase md, String nick){
        return viewerRepo.findViewerByNick(md, nick);
    }

    public void setViewerToOnlineOrCreateIfNotExisting(MongoDatabase md, String nick){
        if(viewerRepo.isViewerExisting(md, nick)){
            viewerRepo.updateWatchingState(md, nick, true);
        } else {
            Viewer v = new Viewer();
            v.nick = nick;
            v.watching = true;
            viewerRepo.saveViewer(md, v);
        }
    }

    /**
     * Sets the viewer to offline. At this state we assume the user was already added to the database before.
     * @param nick The nick to change the watching state for.
     */
    public void setViewerToOffline(MongoDatabase md, String nick) {
        viewerRepo.updateWatchingState(md, nick, false);
    }

    public void setAllViewersToOffline(MongoDatabase md) {
        viewerRepo.updateWatchingStateForAllUsers(md, false);
    }
}
