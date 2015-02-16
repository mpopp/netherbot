package chatbot.services;

import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class TicketService {

    public Viewer findRandomKeyByTicketChance(Map<Viewer, Long> ticketmap){
        long totalTickets = 0L;
        for(Long tickets : ticketmap.values()){
            totalTickets += tickets;
        }

        for(Map.Entry<Viewer, Long> usertickets: ticketmap.entrySet()){
            totalTickets -= usertickets.getValue();
            if(totalTickets <= 0){
                return usertickets.getKey();
            }
        }
        return null;
    }
}
