package integration.repository;

import chatbot.repositories.impl.TwitchAPIRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Matthias Popp
 */
public class TwitchAPIRepositoryTest {

    private TwitchAPIRepository target;

    @Before
    public void setUpClass() {
        target = new TwitchAPIRepository();
    }

    @Test
    public void findFollowersForGamingDaddies(){
       //No exception means test successful, therefore no assertions
       target.findFollowersForChannel("gamingDaddies");
    }

}
