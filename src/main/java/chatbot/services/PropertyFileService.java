package chatbot.services;

import chatbot.entities.Viewer;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class PropertyFileService {

    public Set<String> loadAllPropertyKeysFromFileByValue(Properties file, String value){
        Set<String> properties = new HashSet<String>();
        final Enumeration<?> enumeration = file.propertyNames();
        while(enumeration.hasMoreElements()){
            final String key = (String) enumeration.nextElement();
            String val = file.getProperty(key);
            if(val.toLowerCase().equals(value)){
                properties.add(key);
            }
        }
        return properties;
    }

    public Properties loadPropertiesFile(String propertiesFileName) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(propertiesFileName);
        prop.load(fis);
        fis.close();
        return prop;
    }

    public void clearPropertyFile(String targetFile) throws IOException {
        new PrintWriter(targetFile).close();
    }

    //TODO That method is not generic enough to deserve being placed in a general PropertyFileService. Should be
    // moved to viewer or raffle service.
    public void saveAllProperties(Map<Viewer, Long> properties, String targetFile) throws IOException {
        final Properties pf = loadPropertiesFile(targetFile);
        for(Map.Entry<Viewer, Long> entry : properties.entrySet()) {
            pf.setProperty(entry.getKey().nick, entry.getValue().toString());
        }
        FileOutputStream fos = new FileOutputStream(targetFile);
        pf.store(fos, null);
        fos.close();
    }
}
