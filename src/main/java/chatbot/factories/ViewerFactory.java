package chatbot.factories;

import chatbot.entities.Viewer;
import com.google.common.collect.Sets;
import org.pircbotx.User;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by matthias on 04.03.2015.
 */
@Component
public final class ViewerFactory {
    public static Viewer createViewer(User u){
        Viewer v = new Viewer();
        v.nick = u.getNick();
        v.watching = false;
        return v;
    }

    public static Set<Viewer> createViewers(Set<User> users){
        Set<Viewer> viewers = Sets.newHashSet();
        for(User u: users){
            viewers.add(createViewer(u));
        }
        return viewers;
    }
}
