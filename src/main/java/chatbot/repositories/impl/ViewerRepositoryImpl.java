package chatbot.repositories.impl;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {

    @Override
    public Viewer findViewerByNick(EntityManager em, String nick) {
       return em.find(Viewer.class, nick);
    }

    @Override
    public Set<Viewer> findCurrentViewers(EntityManager em) {
        return Sets.newHashSet(em.createNamedQuery(Viewer.FIND_CURRENT_VIEWERS).getResultList());
    }

    @Override
    public Viewer saveViewer(EntityManager em, Viewer v){
        return em.merge(v);
    }

    @Override
    public void saveViewers(EntityManager em, Set<Viewer> viewers) {
        for(Viewer v : viewers){
            em.merge(v);
        }
    }

    @Override
    public void removeViewerByNick(EntityManager em, String nick) {
        em.createNamedQuery(Viewer.DELETE_BY_NICK).setParameter("nick", nick).executeUpdate();
    }

    @Override
    public boolean isViewerExisting(EntityManager em, String nick) {
        final Viewer v = em.find(Viewer.class, nick);
        return v != null;
    }

    @Override
    public void updateWatchingState(EntityManager em, String nick, boolean watching) {
        em.createNamedQuery(Viewer.UPDATE_WATCHING_STATE)
                .setParameter("watching", watching)
                .setParameter("nick", nick).executeUpdate();
    }

    @Override
    public void updateWatchingStateForAllUsers(EntityManager em, boolean watching) {
        //TODO implement this method
    }
}
