package chatbot.services;

import chatbot.repositories.impl.Propertyfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class RaffleService {

    private final PropertyFileService propertyFileService;

    @Autowired
    public RaffleService(PropertyFileService propertyFileService){

        this.propertyFileService = propertyFileService;
    }

    public void addTickets(Map<String, Long> usertickets, String nick, Long nrOfTicketsToAdd) {
        Long tickets = 1L;
        if (usertickets.containsKey(nick)) {
            tickets = usertickets.get(nick) + nrOfTicketsToAdd;
        }
        usertickets.put(nick, tickets);
        System.out.println(String.format("user %s has %s tickets", nick, tickets));
    }

    public void removeUser(Map<String, Long> usertickets, String nick) {
        usertickets.remove(nick);
    }

    public Map<String, Long> refreshRaffle() throws IOException {
        propertyFileService.clearPropertyFile(Propertyfiles.RAFFLE_TICKET_BACKUP);
        return new HashMap<String, Long>();
    }

    public Map<String, Long> loadRaffle() throws IOException {
        Map<String, Long> raffle = new HashMap<String, Long>();
        final Properties properties = propertyFileService.loadPropertiesFile(Propertyfiles.RAFFLE_TICKET_BACKUP);
        final Enumeration<Object> keys = properties.keys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            Long tickets = Long.parseLong((String) properties.get(key));
            raffle.put(key, tickets);
        }
        return raffle;
    }

    public void saveRaffle(Map<String, Long> usertickets) {
        try {
            propertyFileService.saveAllProperties(usertickets, Propertyfiles.RAFFLE_TICKET_BACKUP);
        } catch (IOException e) {
            System.err.println("Saving the raffle data into " + Propertyfiles.RAFFLE_TICKET_BACKUP + " failed. If the" +
                    " bot dies or is shut down you lose the raffle data of the current session.");
        }
    }
}
