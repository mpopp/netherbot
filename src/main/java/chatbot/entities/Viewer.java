package chatbot.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by matthias.popp on 11.02.2015.
 *
 * Viewers are the base user entities for this chat bot. They contain all the basic data you should need to identify
 * and work with a certain chat user.
 *
 * Viewers are identified by a unique nick.
 */
@Entity
@Table(name="Viewers")
public class Viewer {

    @Id
    public String nick;

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
