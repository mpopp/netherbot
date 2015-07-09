package chatbot.repositories.impl;

import chatbot.core.MongoDbConfiguration;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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
        return new HashSet<Viewer>(dbConfiguration.getDatastore().find(Viewer.class, "watching", true).asList());
    }

    //TODO: REMOVE SYSOUTS AFTER PROBLEM IS FIXED
    @Override
    public Viewer saveViewer(Viewer v) {
        System.out.println("### SAVED VIEWER " + v.nick + " - " + v.wallet.toString() + " ###");
        dbConfiguration.getDatastore().save(v);
        return findViewerByNick(v.nick);

    }

    @Override
    public void saveViewers(Set<Viewer> viewers) {
        List<String> failedSaves = trySave(viewers);
        throwOnFailures(failedSaves);
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
    public void deleteAll() {
        dbConfiguration.getDatastore().delete(dbConfiguration.getDatastore().find(Viewer.class));
    }

    @Override
    public void updateWatchingStateForAllUsers(boolean watching) {
        Datastore ds = dbConfiguration.getDatastore();
        Query<Viewer> findAll = ds.find(Viewer.class, "watching", !watching);
        UpdateOperations<Viewer> updateOperation = ds.createUpdateOperations(Viewer.class).set("watching", watching);
        ds.update(findAll, updateOperation);
    }

    //region HELPERS
    private void throwOnFailures(List<String> failedSaves) {
        if(!failedSaves.isEmpty()){
            String cause = "Saving the following viewers failed because of concurrent modification: " + Joiner.on(",").join(failedSaves);
            throw new ConcurrentModificationException(cause);
        }
    }

    private List<String> trySave(Set<Viewer> viewers) {
        List<String> failedSaves = Lists.newArrayList();
        for (Viewer v : viewers) {
            try {
                saveViewer(v);
            } catch (ConcurrentModificationException e){
                failedSaves.add(v.nick);
            }
        }
        return failedSaves;
    }
    //endregion
}
