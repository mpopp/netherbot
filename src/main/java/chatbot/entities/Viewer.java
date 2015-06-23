package chatbot.entities;

import com.mongodb.ReflectionDBObject;

import javax.persistence.*;

/**
 * Created by matthias.popp on 11.02.2015.
 *
 * Viewers are the base user entities for this chat bot. They contain all the basic data you should need to identify
 * and work with a certain chat user.
 *
 * Viewers are identified by a unique nick.
 */

public class Viewer extends ReflectionDBObject {
    public static final String COLLECTION_NAME = "viewer";
    public static final String FIND_CURRENT_VIEWERS = "findCurrentViewers";
    public static final String DELETE_BY_NICK = "deleteViewerByNick";
    public static final String UPDATE_WATCHING_STATE = "updateWatchingState";

    @Id
    public String nick;

    @Column(nullable = false)
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
