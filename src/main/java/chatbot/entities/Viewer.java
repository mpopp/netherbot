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

    //region BUILDER
    public static class ViewerBuilder {
        private String nick;
        private boolean watching;
        private Wallet wallet;
        private boolean following;
        private boolean subscribed;
        private long level;
        private boolean notificationsEnabled;

        public ViewerBuilder withNick(String nick) {
            this.nick = nick;
            return this;
        }

        public ViewerBuilder withWatching(boolean watching) {
            this.watching = watching;
            return this;
        }

        public ViewerBuilder withFollowing(boolean following) {
            this.following = following;
            return this;
        }

        public ViewerBuilder withSubscribed(boolean subscribed) {
            this.subscribed = subscribed;
            return this;
        }

        public ViewerBuilder withNotificationsEnabled(boolean enabled) {
            this.notificationsEnabled = enabled;
            return this;
        }

        public ViewerBuilder withWallet(Wallet wallet) {
            this.wallet = wallet;
            return this;
        }

        public ViewerBuilder withLevel(long level) {
            this.level = level;
            return this;
        }

        public Viewer create(){
            Viewer v = new Viewer();
            v.nick = nick;
            v.notificationsEnabled = notificationsEnabled;
            v.wallet = wallet;
            v.setLevel(level);
            v.following = following;
            v.watching = watching;
            v.subscribed = subscribed;
            return v;
        }
    }
    //endregion

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
