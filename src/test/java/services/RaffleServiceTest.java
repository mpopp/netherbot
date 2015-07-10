package services;

import chatbot.entities.Viewer;
import chatbot.entities.Wallet;
import chatbot.factories.ViewerFactory;
import chatbot.services.RaffleService;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testutils.ViewerUtils;

import static org.junit.Assert.*;

/**
 * @author Matthias Popp
 */
public class RaffleServiceTest {

    RaffleService target;

    @Before
    public void setUp(){
    target = new RaffleService(null);
    }

    @Test
    public void testEmptyRaffle(){
        Viewer winner = target.findWinner(Sets.<Viewer>newHashSet());
        assertNull(winner);
    }

    @Test
    public void testSingleRaffle(){
        Viewer winner = target.findWinner(ViewerUtils.getTenViewers());
        assertNotNull(winner);
    }

    @Test
    public void testRaffleWithCertainWinner(){
        Viewer highChance = ViewerFactory.build().withNick("winner").withWallet(new Wallet(100, 10)).create();
        Viewer noChance = ViewerFactory.build().withNick("noChance").withWallet(new Wallet(100, 0)).create();

        //The viewer with all the chance has to win all the time.
        for(int i = 0; i < 100; i++) {
            Viewer winner = target.findWinner(Sets.newHashSet(highChance, noChance));
            assertEquals("winner", winner.nick);
        }
    }

}
