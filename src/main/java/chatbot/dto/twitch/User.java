package chatbot.dto.twitch;

/**
 * @author Matthias Popp
 */
public class User {
    private final String name                 ;
            private final String display_name;

    public User(String name, String display_name) {
        this.name = name;
        this.display_name = display_name;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return display_name;
    }
}
