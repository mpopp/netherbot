package chatbot.commands;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.entities.Viewer;
import chatbot.services.RaffleService;
import chatbot.services.TicketService;
import chatbot.services.UserRolesService;
import com.google.common.collect.Sets;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by matthias.popp on 03.02.2015.
 */
@Component
public class Raffle extends AbstractCommand {

    private final TicketService ticketService;
    private final UserRolesService userRolesService;
    private final RaffleService raffleService;
    private Set<Viewer> previousWinners;


    @Autowired
    public Raffle(final TicketService ticketService, final UserRolesService userRolesService, final RaffleService raffleService) {
        this.ticketService = ticketService;
        this.userRolesService = userRolesService;
        this.raffleService = raffleService;
        this.previousWinners = Sets.newHashSet();
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        Set<Viewer> raffleParticipants = raffleService.findRaffleParticipants(previousWinners);
        Viewer winner = raffleService.findWinner(raffleParticipants);
        //TODO set session points for winner to 0
        previousWinners.add(winner);
    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        return userRolesService.isUserOperatorInChannel(event.getUser(), event.getChannel());
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return message.equals(PatternConstants.PREFIX + "giveaway");
    }


}