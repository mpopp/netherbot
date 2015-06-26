package chatbot.repositories.impl;

import chatbot.core.MongoDbConfiguration;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {

    @Autowired
    private MongoDbConfiguration dbConfiguration;

    @Override
    public Viewer findViewerByNick(String nick) {
        List<Viewer> viewers = dbConfiguration.getDatastore().find(Viewer.class, "nick", nick).limit(1).asList();
        if (viewers.size() == 1) {
            return viewers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Set<Viewer> findCurrentViewers() {
        //"SELECT v FROM Viewer v WHERE v.watching = " +"'true'"),
        return new HashSet<Viewer>(dbConfiguration.getDatastore().find(Viewer.class, "watching", true).limit(1).asList());
    }

    //TODO: REMOVE SYSOUTS AFTER PROBLEM IS FIXED
    @Override
    public Viewer saveViewer(Viewer v) {
        System.out.println("### SAVE VIEWER ###");
        System.out.println(v.nick);
        System.out.println(v.wallet.sessionPoints + " - " + v.wallet.totalPoints);


        dbConfiguration.getDatastore().save(v);
        return findViewerByNick(v.nick);

    }

    @Override
    public void saveViewers(Set<Viewer> viewers) {
        for (Viewer v : viewers) {
            saveViewer(v);
        }
    }

    @Override
    public void removeViewerByNick(String nick) {
        //DELETE FROM Viewer v WHERE v.nick = :nick"
        dbConfiguration.getDatastore().delete(findViewerByNick(nick));
    }

    @Override
    public boolean isViewerExisting(String nick) {
        final Viewer v = findViewerByNick(nick);
        return v != null;
    }

    @Override
    public void updateWatchingState(String nick, boolean watching) {
        //UPDATE Viewer SET watching = :watching " "WHERE nick = :nick"
        Viewer viewerByNick = findViewerByNick(nick);
        viewerByNick.watching = watching;
        saveViewer(viewerByNick);
    }

    @Override
    public void updateWatchingStateForAllUsers(boolean watching) {
        //TODO implement this method
    }
}
