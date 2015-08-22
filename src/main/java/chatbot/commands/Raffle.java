package chatbot.commands;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.entities.Viewer;
import chatbot.services.RaffleService;
import chatbot.services.UserRolesService;
import chatbot.services.ViewerService;
import com.google.common.collect.Sets;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by matthias.popp on 03.02.2015.
 */
@Component
public class Raffle extends AbstractCommand {

    private ViewerService viewerService;
    private final UserRolesService userRolesService;
    private final RaffleService raffleService;


    @Autowired
    public Raffle(final ViewerService viewerService, final UserRolesService userRolesService, final RaffleService raffleService) {
        this.viewerService = viewerService;
        this.userRolesService = userRolesService;
        this.raffleService = raffleService;
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        Set<Viewer> raffleParticipants = raffleService.findRaffleParticipants();
        Viewer winner = raffleService.findWinner(raffleParticipants);
        winner.wallet.sessionPoints = 0L;
        viewerService.forceSave(winner);
        event.getChannel().send().message(winner.nick + " WON THE RAFFLE! Send a private message to GamingDaddies on Twitch to receive your prize.");
    }

    @Override
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        HashSet<String> raffleUsers = Sets.newHashSet("gamingdaddies", "netherbrain");
        return raffleUsers.contains(event.getUser().getNick().toLowerCase());
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        return message.equals(PatternConstants.PREFIX + "giveaway");
    }

}