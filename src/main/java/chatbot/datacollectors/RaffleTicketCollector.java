package chatbot.datacollectors;

import chatbot.ircbot.BotWrapper;
import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import chatbot.services.RaffleService;
import org.pircbotx.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by matthias.popp on 04.02.2015.
 */
@Component
public class RaffleTicketCollector implements Runnable {
    private static final long SLEEP_INTERVAL = 2000; //60000;


    private Map<String, Long> usertickets;
    private final Set<String> blacklist;

    @Autowired
    private BotWrapper bot;

    @Autowired
    private RaffleService raffleService;

    private Boolean run;

    @Autowired
    public RaffleTicketCollector(PropertyFileService propertyFileService){
        this.usertickets = new HashMap<String, Long>();
        this.blacklist = new HashSet<String>();
        try {
            final Properties properties = propertyFileService.loadPropertiesFile(Propertyfiles.BLACKLIST);
            blacklist.addAll(propertyFileService.loadAllPropertyKeysFromFileByValue(properties, "raffle"));
        } catch (IOException e) {
            System.err.println("Something went wrong when trying to load the blacklist for raffles. Continuing with " +
                    "empty blacklist!");
        }

        this.run = true;
    }

    public void stop() {
        run = false;
    }

    public void reset() throws IOException {
        usertickets = raffleService.refreshRaffle();
    }

    public void start() throws IOException {
        usertickets = raffleService.loadRaffle();
        run = true;
    }

    public Map<String, Long> getUsers(){
        return usertickets;
    }



    @Override
    public void run() {
        while (run) {
            for (User user : bot.getBot().getUserChannelDao().getAllUsers()) {
                String nick = user.getNick().toLowerCase();
                if(!blacklist.contains(nick)) {
                    Long nrOfTicketsToAdd = getNrOfTicketsToAdd(nick);
                    raffleService.addTickets(usertickets, nick, nrOfTicketsToAdd);
                } else {
                    raffleService.removeUser(usertickets, nick);
                }
            }
            raffleService.saveRaffle(usertickets);
            try {
                Thread.sleep(SLEEP_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Collecting tickets has stopped");
    }

    private Long getNrOfTicketsToAdd(String nick) {
        return 1L;
    }
}
