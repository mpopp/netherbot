package chatbot.dto.twitch;
/**
 * @author Matthias Popp
 */

public class Followers {
    private final Follower[] follows;
    private final int _total;

    public Followers(Follower[] follows, int total) {
        this.follows = follows;
        this._total = total;
    }

    public int getTotal() {
        return _total;
    }

    public Follower[] getFollowers() {
        return follows;
    }
}
