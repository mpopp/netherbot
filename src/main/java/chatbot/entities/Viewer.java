package chatbot.entities;

import javax.persistence.*;

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
@NamedQueries(
        value = {
                @NamedQuery(name = Viewer.FIND_CURRENT_VIEWERS, query = "SELECT v FROM Viewer v WHERE v.watching = " +
                        "'true'"),
                @NamedQuery(name = Viewer.DELETE_BY_NICK, query = "DELETE v FROM Viewer v WHERE v.nick = :nick"),
                @NamedQuery(name = Viewer.UPDATE_WATCHING_STATE, query = "UPDATE Viewer SET watching = :watching " +
                        "WHERE nick = :nick")
        }
)
public class Viewer {

    public static final String FIND_CURRENT_VIEWERS = "findCurrentViewers";
    public static final String DELETE_BY_NICK = "deleteViewerByNick";
    public static final String UPDATE_WATCHING_STATE = "updateWatchingState";

    @Id
    public String nick;

    @Column(nullable = false)
    public boolean watching;

    @Embedded
    public Wallet wallet;

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
