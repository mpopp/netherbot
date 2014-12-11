package chatbot.repositories;

import chatbot.repositories.impl.MessageRespondRepositoryImpl;
import chatbot.repositories.impl.Propertyfiles;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 29.11.13
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class MessageRespondRepositoryImplTest {

    private MessageRespondRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = new MessageRespondRepositoryImpl();
    }

    @After
    public void cleanUp() {
      new File(Propertyfiles.MESSAGE_RESPOND_REPO_FILE).delete();
    }

    @Test
    public void findMessageForCommandNull() throws Exception {
        Assert.assertNull(repository.findMessageForCommand("!command"));
    }

    @Test
    public void addCommand() throws Exception {
        String message = "message";
        String command = "!command";
        repository.addCommand(command, message);
        Assert.assertEquals(message, repository.findMessageForCommand(command));
    }

    @Test
    public void  ignoreCommandsWithoutPrefix(){
        String message = "message";
        String command = "asdf";
        repository.addCommand(command, message);
        Assert.assertNull(repository.findMessageForCommand(command));
    }
}
