package chatbot.entities;

import com.mongodb.ReflectionDBObject;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;



/**
 * Created by matthias.popp on 11.02.2015.
 *
 * Viewers are the base user entities for this chat bot. They contain all the basic data you should need to identify
 * and work with a certain chat user.
 *
 * Viewers are identified by a unique nick.
 */

@Entity("viewer")
public class Viewer {

    @Id
    public String nick;

    public boolean watching;

    @Embedded
    public Wallet wallet;

    public Viewer(){
        wallet = new Wallet();
        watching = false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Viewer){
            return nick.equals(((Viewer) obj).nick);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nick.hashCode();
    }
}
