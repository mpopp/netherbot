package chatbot.repositories.impl;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by matthias.popp on 11.02.2015.
 */
@Component
public class ViewerRepositoryImpl implements ViewerRepository {

    private final PersistenceUtils persistenceUtils;

    @Autowired
    public ViewerRepositoryImpl(PersistenceUtils persistenceUtils){
        this.persistenceUtils = persistenceUtils;
    }

    @Override
    public Viewer findViewerByNick(String nick) {
       return persistenceUtils.openEm().find(Viewer.class, nick);
    }

    @Override
    public Set<Viewer> findCurrentViewers() {
        final EntityManager em = persistenceUtils.openEm();
        Set<Viewer> currentViewers = Sets.newHashSet(em.createNamedQuery(Viewer.FIND_CURRENT_VIEWERS).getResultList());
        persistenceUtils.closeEm(em);
        return currentViewers;
    }

    @Override
    public Viewer saveViewer(Viewer v){
        final EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        final Viewer merged = em.merge(v);
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
        return merged;
    }

    @Override
    public void saveViewers(Set<Viewer> viewers) {
    final EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        for(Viewer v : viewers){
            em.merge(v);
        }
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
    }

    @Override
    public void removeViewerByNick(String nick) {
        final EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        em.createNamedQuery(Viewer.DELETE_BY_NICK).setParameter("nick", nick).executeUpdate();
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
    }

    @Override
    public boolean isViewerExisting(String nick) {
        final EntityManager em = persistenceUtils.openEm();
        final Viewer v = em.find(Viewer.class, nick);
        persistenceUtils.closeEm(em);
        return v != null;
    }

    @Override
    public void updateWatchingState(String nick, boolean watching) {
        final EntityManager em = persistenceUtils.openEm();
        persistenceUtils.startTransaction(em);
        em.createNamedQuery(Viewer.UPDATE_WATCHING_STATE)
                .setParameter("watching", watching)
                .setParameter("nick", nick).executeUpdate();
        persistenceUtils.commitTransaction(em);
        persistenceUtils.closeEm(em);
    }

    @Override
    public void updateWatchingStateForAllUsers(boolean watching) {
        //TODO implement this method
    }
}
