package chatbot.repositories.impl;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {

    Map<String, Viewer> viewers;

    @Autowired private PersistenceUtils persistenceUtils;

    public ViewerRepositoryImpl(){
        viewers = new HashMap<String,Viewer>();
    }

    @Override
    public Viewer findViewerByNick(String nick) {
        return viewers.get(nick);
    }

    @Override
    public Set<Viewer> findCurrentViewers() {
        return new HashSet<Viewer>(viewers.values());
    }

    @Override
    public void addViewer(Viewer v) {
        viewers.put(v.nick, v);
    }

    @Override
    public void removeViewerByNick(String nick) {
        viewers.remove(nick);
    }
}
