package chatbot.commands.base;

import chatbot.core.PatternConstants;
import chatbot.datacollectors.RaffleTicketCollector;
import chatbot.entities.Viewer;
import chatbot.services.TicketService;
import chatbot.services.UserRolesService;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Created by matthias.popp on 03.02.2015.
 */
@Component
public class Raffle extends AbstractCommand {

    private Map<String, Long> tickets;
    private Boolean run;

    @Autowired private RaffleTicketCollector collector;
    @Autowired private TicketService ticketService;
    @Autowired private UserRolesService userRolesService;

    public Raffle(){
        run = false;
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        String eventMessage = event.getMessage().toLowerCase();

        try {
            if (!run) {
                startRaffle(event, eventMessage);
            } else {
                stopRaffle(event, eventMessage);
            }
        } catch(IOException e){
            event.getChannel().send().message("Gewinnspiel konnte aufgrund eines Fehlers nicht gestartet werden.");
        }
    }

    private void stopRaffle(MessageEvent event, String eventMessage) throws IOException {
        if(eventMessage.equals(PatternConstants.PREFIX + "stopraffle")){
            run = false;
            collector.stop();
            Viewer winner = ticketService.findRandomKeyByTicketChance(collector.getUsers());
            collector.reset();
            event.getChannel().send().message(String.format("Gewonnen hat %s", winner!= null ? winner.nick : "-"));
        } else {
            event.getChannel().send().message("Es läuft bereits ein Gewinnspiel. Stoppe das Gewinnspiel mit " +
                    "!stopraffle und ermittle damit automatisch den Gewinner. (MODS ONLY)");
        }
    }

    private void startRaffle(MessageEvent event, String eventMessage) throws IOException {
        if(eventMessage.equals(PatternConstants.PREFIX + "startraffle")){
            run = true;
            collector.start();
            Thread t = new Thread(collector);
            t.start();

            event.getChannel().send().message(
                    "Gewinnspiel wurde gestartet. Mit !stopraffle das Gewinnspiel stoppen und automatisch den Gewinner ermitteln.");
        } else {
            event.getChannel().send().message("Es wurde kein Gewinnspiel gestartet. Starte ein Gewinnspiel mit " +
                    "!startraffle. (MODS ONLY)");
        }
    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        return userRolesService.isUserOperatorInChannel(event.getUser(), event.getChannel());
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        String eventMessage = message.toLowerCase();
        return message.equals(PatternConstants.PREFIX + "startraffle") || message.equals(PatternConstants.PREFIX +
                "stopraffle");
    }


}