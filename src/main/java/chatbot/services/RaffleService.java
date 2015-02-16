package chatbot.services;

import chatbot.entities.Viewer;
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

    public void addTickets(Map<Viewer, Long> usertickets, Viewer viewer, Long nrOfTicketsToAdd) {
        Long tickets = 1L;
        if (usertickets.containsKey(viewer)) {
            tickets = usertickets.get(viewer) + nrOfTicketsToAdd;
        }
        usertickets.put(viewer, tickets);
        System.out.println(String.format("user %s has %s tickets", viewer.nick, tickets));
    }

    public void removeUser(Map<Viewer, Long> usertickets, Viewer viewer) {
        usertickets.remove(viewer);
    }

    public Map<Viewer, Long> refreshRaffle() throws IOException {
        propertyFileService.clearPropertyFile(Propertyfiles.RAFFLE_TICKET_BACKUP);
        return new HashMap<Viewer, Long>();
    }

    public Map<Viewer, Long> loadRaffle() throws IOException {
        Map<Viewer, Long> raffle = new HashMap<Viewer, Long>();
        final Properties properties = propertyFileService.loadPropertiesFile(Propertyfiles.RAFFLE_TICKET_BACKUP);
        final Enumeration<Object> keys = properties.keys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            Long tickets = Long.parseLong((String) properties.get(key));
            Viewer v = new Viewer();
            v.nick = key;
            raffle.put(v, tickets);
        }
        return raffle;
    }

    public void saveRaffle(Map<Viewer, Long> usertickets) {
        try {
            propertyFileService.saveAllProperties(usertickets, Propertyfiles.RAFFLE_TICKET_BACKUP);
        } catch (IOException e) {
            System.err.println("Saving the raffle data into " + Propertyfiles.RAFFLE_TICKET_BACKUP + " failed. If the" +
                    " bot dies or is shut down you lose the raffle data of the current session.");
        }
    }
}