package chatbot.entities;

/**
 * Created by matthias.popp on 11.02.2015.
 *
 * Viewers are the base user entities for this chat bot. They contain all the basic data you should need to identify
 * and work with a certain chat user.
 *
 * Viewers are identified by a unique nick.
 */
public class Viewer {
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
