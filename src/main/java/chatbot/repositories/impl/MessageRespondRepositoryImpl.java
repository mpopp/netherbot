package chatbot.repositories.impl;

import chatbot.core.PatternConstants;
import chatbot.repositories.api.MessageRespondRepository;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: poppmat
 * Date: 29.11.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "PropertyMessageRespondImpl")
public class MessageRespondRepositoryImpl implements MessageRespondRepository {
    private Properties prop;

    @Override
    public String findMessageForCommand(String commandPattern) {
        return getPropertiesFile().getProperty(commandPattern);
    }

    @Override
    public void addCommand(String pattern, String message) {
        //We want to avoid adding commands without the ! prefix. If we would not do that, we will get in trouble if
        // someone text which starts with a command word accidentally.
        if (pattern.startsWith(PatternConstants.PREFIX)) {
            getPropertiesFile().setProperty(pattern, message);
            try {
                FileOutputStream fos = new FileOutputStream(Propertyfiles.MESSAGE_RESPOND_REPO_FILE);
                getPropertiesFile().store(fos, null);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    /****************************************************************
     * private helpers
     */

    /**
     * Get the properties file for loading commands. Lazily initialized on the first call. Nothing happens if no
     * properties file was created so far.
     *
     * @return The properties file.
     */
    private Properties getPropertiesFile() {
        if (prop == null) {
            prop = new Properties();
            try {
                FileInputStream fis = new FileInputStream(Propertyfiles.MESSAGE_RESPOND_REPO_FILE);
                prop.load(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                try {
                    FileOutputStream fos = new FileOutputStream(Propertyfiles.MESSAGE_RESPOND_REPO_FILE);
                    fos.close();
                } catch (IOException e1) {
                    System.err.println("Unable to create file " + Propertyfiles.MESSAGE_RESPOND_REPO_FILE + ", no commands can be added");
                }
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return prop;
    }
}
