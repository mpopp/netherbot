package chatbot.services;

import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by matthias on 26.06.2015.
 */
@Component
public class LevelCurveService {

    public long calculateLevel(Viewer v){
        double levelPoints = 1.0;
        long level = 1;
        while(v.wallet.totalPoints > levelPoints){
            levelPoints *= 1.4;
            level++;
            System.out.println("Level " + level + " reached at " + levelPoints + " points. After" + (levelPoints / 2)/3600 + " seconds.");
        }
        return level;
    }

    public void adjustLevels(Set<Viewer> viewers){
        for(Viewer v : viewers){
            v.setLevel(calculateLevel(v));
        }
    }
}
