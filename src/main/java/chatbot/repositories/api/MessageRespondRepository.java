package chatbot.repositories.api;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 29.11.13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public interface MessageRespondRepository {
    /**
     * Returns the command message or null.
     *
     * @param commandPattern The pattern to search for.
     * @return The command message associated with the pattern or null.
     */
    String findMessageForCommand(String commandPattern);

    /**
     * Adds a new command to the repository.
     *
     * @param pattern The command pattern (e.g. !showMessage)
     * @param message The Mesasge to display for that command pattern.
     */
    void addCommand(String pattern, String message);
}
