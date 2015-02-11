package chatbot.services;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class TicketService {

    public String findRandomKeyByTicketChance(Map<String, Long> ticketmap){
        long totalTickets = 0L;
        for(Long tickets : ticketmap.values()){
            totalTickets += tickets;
        }

        for(Map.Entry<String, Long> usertickets: ticketmap.entrySet()){
            totalTickets -= usertickets.getValue();
            if(totalTickets <= 0){
                return usertickets.getKey();
            }
        }
        return "-";
    }
}
