package chatbot.repositories.impl;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {

    @Override
    public Viewer findViewerByNick(Datastore ds, String nick) {
        List<Viewer> viewers = ds.find(Viewer.class, "nick", nick).limit(1).asList();
        if (viewers.size() == 1) {
            return viewers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Set<Viewer> findCurrentViewers(Datastore ds) {
        //"SELECT v FROM Viewer v WHERE v.watching = " +"'true'"),
        return new HashSet<Viewer>(ds.find(Viewer.class, "watching", true).limit(1).asList());
    }

    @Override
    public Viewer saveViewer(Datastore ds, Viewer v) {
        ds.save(v);
        return findViewerByNick(ds, v.nick);

    }

    @Override
    public void saveViewers(Datastore ds, Set<Viewer> viewers) {
        for (Viewer v : viewers) {
            saveViewer(ds, v);
        }
    }

    @Override
    public void removeViewerByNick(Datastore ds, String nick) {
        //DELETE FROM Viewer v WHERE v.nick = :nick"
        ds.delete(findViewerByNick(ds, nick));
    }

    @Override
    public boolean isViewerExisting(Datastore ds, String nick) {
        final Viewer v = findViewerByNick(ds, nick);
        return v != null;
    }

    @Override
    public void updateWatchingState(Datastore ds, String nick, boolean watching) {
        //UPDATE Viewer SET watching = :watching " "WHERE nick = :nick"
        Viewer viewerByNick = findViewerByNick(ds, nick);
        viewerByNick.watching = watching;
        saveViewer(ds, viewerByNick);
    }

    @Override
    public void updateWatchingStateForAllUsers(Datastore ds, boolean watching) {
        //TODO implement this method
    }
}
