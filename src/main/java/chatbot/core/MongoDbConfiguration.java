package chatbot.core;

import chatbot.entities.Viewer;
import chatbot.repositories.impl.Propertyfiles;
import chatbot.services.PropertyFileService;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Bernhard
 * Date: 24.06.15
 * Time: 00:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MongoDbConfiguration {


    PropertyFileService propertyFileService;

    Datastore datastore;

    public MongoDbConfiguration() {
        this.propertyFileService = new PropertyFileService();
        try {
            Properties prop = getConfigurationProperties();

            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(
                    prop.getProperty("database-user"),
                    prop.getProperty("database-name"),
                    prop.getProperty("database-password").toCharArray());
            ServerAddress serverAddress = new ServerAddress(prop.getProperty("database-host"), Integer.parseInt(prop.getProperty("database-port")));
            List<MongoCredential> mongoCredentials = new ArrayList<MongoCredential>();
            mongoCredentials.add(mongoCredential);
            MongoClient mongoClient = new MongoClient(serverAddress, mongoCredentials);
            Morphia morphia = new Morphia();
            morphia.map(Viewer.class);
            datastore = morphia.createDatastore(mongoClient, prop.getProperty("database-name"));
        } catch (IOException e) {

            datastore = null;
        }


    }

    private Properties getConfigurationProperties() throws IOException {
        return propertyFileService.loadPropertiesFile(Propertyfiles.BOT_CONNECTION_PARAMETERS);
    }


    public Datastore getDatastore() {
        return datastore;
    }


}
