package chatbot.dto.twitch;

/**
 * @author Matthias Popp
 */
public class Follower {
    private final boolean notifications;
    private final User user;

    public Follower(boolean notifications, User user) {
        this.notifications = notifications;
        this.user = user;
    }


    public boolean isNotifications() {
        return notifications;
    }

    public User getUser() {
        return user;
    }
}
