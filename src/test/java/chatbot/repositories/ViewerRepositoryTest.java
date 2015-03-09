package chatbot.repositories;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.ViewerRepositoryImpl;
import chatbot.repositories.utils.PersistenceUtils;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Created by matthias on 01.03.2015.
 */
public class ViewerRepositoryTest {

    PersistenceUtils persistenceUtils;

    ViewerRepository target;

    @Before
    public void setupClass(){
        persistenceUtils = new PersistenceUtils("netherbot-test");
        target = new ViewerRepositoryImpl(persistenceUtils);
    }

    @Test
    public void testSaveViewers(){
        Viewer v1 = new Viewer();
        v1.nick = "v1";
        v1.watching = true;
        Viewer v2 = new Viewer();
        v2.nick = "v2";
        v2.watching = true;

        target.saveViewers(Sets.newHashSet(v1,v2));
        Set<Viewer> currentViewers = target.findCurrentViewers();
        Assert.assertEquals(2, currentViewers.size());
    }

    @Test
    public void testUpdateWatchingState(){
        Viewer v1 = new Viewer();
        v1.nick = "v1";
        v1.watching = false;
        target.saveViewer(v1);
        target.updateWatchingState("v1", true);
        Set<Viewer> currentViewers = target.findCurrentViewers();
        Assert.assertEquals(1, currentViewers.size());
    }
}
