package chatbot.repositories.impl;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import com.google.common.collect.Sets;
import com.mongodb.ReflectionDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {


    private MongoCollection<Viewer> _getViewerCollection(MongoDatabase md) {
        return md.getCollection(Viewer.COLLECTION_NAME, Viewer.class);
    }

    @Override
    public Viewer findViewerByNick(MongoDatabase md, String nick) {
        List<Viewer> viewers = _getViewerCollection(md)
                .find(Filters.eq("nick", nick))
                .limit(1)
                .into(new ArrayList<Viewer>());
        if (viewers.size() == 1) {
            return viewers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Set<Viewer> findCurrentViewers(MongoDatabase md) {
        //"SELECT v FROM Viewer v WHERE v.watching = " +"'true'"),
        return _getViewerCollection(md).find(Filters.eq("watching", "true")).into(new HashSet<Viewer>());
    }

    @Override
    public Viewer saveViewer(MongoDatabase md, Viewer v) {
        if (isViewerExisting(md, v.nick)) {
            return _getViewerCollection(md).findOneAndReplace(Filters.eq("nick", v.nick), v);
        } else {
            _getViewerCollection(md).insertOne(v);
            return findViewerByNick(md, v.nick);
        }
    }

    @Override
    public void saveViewers(MongoDatabase md, Set<Viewer> viewers) {
        for (Viewer v : viewers) {
            saveViewer(md, v);
        }
    }

    @Override
    public void removeViewerByNick(MongoDatabase md, String nick) {
        //DELETE FROM Viewer v WHERE v.nick = :nick"
        _getViewerCollection(md).deleteOne(Filters.eq("nick", nick));
    }

    @Override
    public boolean isViewerExisting(MongoDatabase md, String nick) {
        final Viewer v = findViewerByNick(md, nick);
        return v != null;
    }

    @Override
    public void updateWatchingState(MongoDatabase md, String nick, boolean watching) {
        //UPDATE Viewer SET watching = :watching " "WHERE nick = :nick"
        Viewer viewerByNick = findViewerByNick(md, nick);
        viewerByNick.watching = watching;
        saveViewer(md, viewerByNick);
    }

    @Override
    public void updateWatchingStateForAllUsers(MongoDatabase md, boolean watching) {
        //TODO implement this method
    }
}
