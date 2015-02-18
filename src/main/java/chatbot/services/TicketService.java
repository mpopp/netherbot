package chatbot.services;

import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class TicketService {

    private final Random rand;

    public TicketService (){
        rand = new Random();
    }

    public Viewer findRandomKeyByTicketChance(Map<Viewer, Long> ticketmap){
        long totalTickets = 0L;
        for(Long tickets : ticketmap.values()){
            totalTickets += tickets;
        }
        long randomTickets = nextRandomLongInInterval(0, totalTickets);

        for(Map.Entry<Viewer, Long> usertickets: ticketmap.entrySet()){
            randomTickets -= usertickets.getValue();
            if(randomTickets <= 0){
                return usertickets.getKey();
            }
        }
        return null;
    }

    public long nextRandomLongInInterval(long min, long max){
        return min + (long)(rand.nextDouble()*(max - min));
    }
}
