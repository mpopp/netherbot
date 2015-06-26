package services;

import chatbot.entities.Viewer;
import chatbot.services.LevelCurveService;
import org.junit.Test;

/**
 * Created by matthias on 26.06.2015.
 */
public class LevelCurveServiceTest  {


    @Test
    public void testLevelCurve(){
        LevelCurveService s = new LevelCurveService();
        Viewer v = new Viewer();
        v.wallet.totalPoints = 200000L;
        long l = s.calculateLevel(v);
        System.out.println("Viewer reached level: " + l);
    }
}
