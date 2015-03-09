package chatbot.repositories;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.ViewerRepositoryImpl;
import chatbot.repositories.utils.PersistenceUtils;
import com.google.common.collect.Sets;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * Created by matthias on 01.03.2015.
 */
public class ViewerRepositoryTest {

    PersistenceUtils persistenceUtils;
    EntityManager em;
    ViewerRepository target;

    @Before
    public void setupClass(){
        persistenceUtils = new PersistenceUtils("netherbot-test");
        em = persistenceUtils.openEm();
        target = new ViewerRepositoryImpl();
    }

    @After
    public void tearDown(){
        persistenceUtils.closeEm(em);
    }



    @Test
    public void testSaveViewers(){
        Viewer v1 = new Viewer();
        v1.nick = "v1";
        v1.watching = true;
        Viewer v2 = new Viewer();
        v2.nick = "v2";
        v2.watching = true;
        persistenceUtils.startTransaction(em);
        target.saveViewers(em, Sets.newHashSet(v1, v2));
        persistenceUtils.commitTransaction(em);
        Set<Viewer> currentViewers = target.findCurrentViewers(em);
        Assert.assertEquals(2, currentViewers.size());
    }

    @Test
    public void testUpdateWatchingState(){
        Viewer v1 = new Viewer();
        v1.nick = "v1";
        v1.watching = false;
        persistenceUtils.startTransaction(em);
        target.saveViewer(em, v1);
        target.updateWatchingState(em, "v1", true);
        persistenceUtils.commitTransaction(em);
        Set<Viewer> currentViewers = target.findCurrentViewers(em);
        Assert.assertEquals(1, currentViewers.size());
    }
}
