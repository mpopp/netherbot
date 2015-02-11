package chatbot.commands.base;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 30.11.13
 * Time: 12:08
 * This command is the abstract base for all message commands. It provides pre and post processing for the messages
 * and some configuration possibility like defining required privileges and timeouts.
 */
public abstract class AbstractCommand extends ListenerAdapter {


    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if (!isCommandUnderstood(event.getMessage())) return;
        if(!isCommandExecutionAllowed(event)){
            event.getChannel().send().message("Sorry, du darfst diesen Befehl nicht ausführen.");
            return;
        }
        preprocessingHook(event);
        executeCommand(event);
        postprocessingHook(event);
    }

    /**
     * Implement the command in this method.
     * @param event The MessageEvent for that command.
     */
    protected abstract void executeCommand(MessageEvent event);

    /**
     * Checks if the command is allowed to be executed or not. You can check e.g. privileges here.
     *
     * @return Default implementation simply allows execution.
     */
    protected boolean isCommandExecutionAllowed(MessageEvent event) {
        return true;
    }

    /**
     * Overwrite for custom command preprocessing.
     */
    protected void preprocessingHook(MessageEvent event) {

    }

    /**
     * Overwrite for custom command postprocessing.
     */
    protected void postprocessingHook(MessageEvent event) {

    }

    /**
     * Implement that method to check if the command was understood by the bot or not. (i.e. check for the command
     * pattern to be right). If the command was not understood no processing is done at all! (also pre and postprocessing
     * is NOT done in that case).
     * @param message The command message.
     * @return <code>true</code> if the command was understood, otherwise <code>false</code>.
     */
    protected abstract boolean isCommandUnderstood(String message);
}
