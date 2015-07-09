package chatbot.entities;

import chatbot.dto.PointIncrement;
import org.mongodb.morphia.annotations.*;


/**
 * Created by matthias.popp on 11.02.2015.
 * <p/>
 * Viewers are the base user entities for this chat bot. They contain all the basic data you should need to identify
 * and work with a certain chat user.
 * <p/>
 * Viewers are identified by a unique nick.
 */

@Entity("viewer")
public class Viewer {

    @Id
    public String nick;

    @Version
    public long version;

    public boolean watching;

    @Embedded
    public Wallet wallet;

    @Transient
    private boolean following;

    @Transient
    private boolean subscribed;

    @Transient
    private long level;

    @Transient
    private boolean notificationsEnabled;

    public Viewer() {
        wallet = new Wallet();
        watching = false;
        following = false;
        notificationsEnabled = false;
        subscribed = false;
    }

    public void collectPoints(PointIncrement pointsToCollect) {
        wallet.sessionPoints += pointsToCollect.getSessionPointIncrement();
        wallet.totalPoints += pointsToCollect.getTotalPointIncrement();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Viewer) {
            return nick.equals(((Viewer) obj).nick);
        }
        return false;
    }

    public long getLevel() {
        return level;
    }

    public long setLevel(long value) {
        return level = value;
    }

    public boolean hasSubscribed() {
        return subscribed;
    }

    public boolean hasNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean value) {
        notificationsEnabled = value;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean value) {
        following = value;
    }

    @Override
    public int hashCode() {
        return nick.hashCode();
    }
}
