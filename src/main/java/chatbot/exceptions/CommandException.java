package chatbot.exceptions;

/**
 * Created by matthias.popp on 10.02.2015.
 */
public class CommandException extends RuntimeException {

    public CommandException(String message){
        super(message);
    }
}
