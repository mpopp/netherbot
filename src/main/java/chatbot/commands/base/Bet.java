package chatbot.commands.base;

import chatbot.core.PatternConstants;
import chatbot.exceptions.CommandException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matthias.popp on 10.02.2015.
 */
//TODO create custom exception for command problems.
//TODO consider Single Level of Abstraction principle. (REFACTORING NEEDED)
public class Bet extends AbstractCommand {

    private final Map<String, Long> currentBets = new HashMap<String, Long>();

    @Override
    protected void executeCommand(MessageEvent event) {

        String nick = event.getUser().getNick().toLowerCase();
        String m = event.getMessage().toLowerCase();

        try {
            //TODO check if bet is placed or bets are going to be evaluated
            final String[] split = splitMessage(event, m);
            final String winOrLose = split[1];
            long points = getPoints(split[2]);

            //TODO validate that points are within budget
            validateFirstBet(nick);

            currentBets.put(nick, points);
        } catch (CommandException e){
            event.respond(e.getMessage());
        }

    }

    private void validateFirstBet(String nick) {
        if (currentBets.containsKey(nick)) {
            throw new CommandException("Du hast für dieses Spiel schon einen Tipp abgegeben.");
        }
    }

    private long getPoints(String pointsAsString){
        try {
            return Long.parseLong(pointsAsString);
        } catch (NumberFormatException e) {
            throw new CommandException("Du hast für die gesetzten Tickets keine gültige Zahl angegeben.");
        }
    }

    private String[] splitMessage(MessageEvent event, String m) {
        final String[] split = StringUtils.split(m, " ");
        if(split.length != 3){
            throw new CommandException("Du hast den Befehl nicht richtig geschrieben. !bet [win/lose] [gesetzte " +
                    "Punkte]");
        }
        return split;
    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        String m = message.toLowerCase();
        return StringUtils.startsWith(m, PatternConstants.PREFIX + "bet");
    }
}
