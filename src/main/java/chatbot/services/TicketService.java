package chatbot.services;

import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class TicketService {

    private final Random rand;

    public TicketService (){
        rand = new Random();
    }

    public Viewer findRandomKeyByTicketChance(Set<Viewer> viewers){
        long totalTickets = 0L;
        for(Viewer viewer : viewers){
            totalTickets += viewer.wallet.sessionPoints;
        }
        long randomTickets = nextRandomLongInInterval(0, totalTickets);

        for(Viewer viewer: viewers){
            randomTickets -= viewer.wallet.sessionPoints;
            if(randomTickets <= 0){
                return viewer;
            }
        }
        return null;
    }

    public long nextRandomLongInInterval(long min, long max){
        return min + (long)(rand.nextDouble()*(max - min));
    }
}
