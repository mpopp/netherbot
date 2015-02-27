package chatbot.services;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.springframework.stereotype.Component;

/**
 * Service for easing the check of the different user roles. We could add user roles to the db as well and access the
 * db here. It is all a tradeoff between having to sync. irc user rights with db and speed of role recognition.
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class UserRolesService {

public boolean isUserOperatorInChannel(final User user, final Channel channel){
    for(User u : channel.getOps()){
        if(u.getNick().equals(user.getNick())){
            return true;
        }
    }
    return false;
}
}
