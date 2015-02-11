package chatbot.services;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.springframework.stereotype.Component;

/**
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
