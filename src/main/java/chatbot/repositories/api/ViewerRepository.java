package chatbot.repositories.api;

import chatbot.entities.Viewer;
import com.mongodb.client.MongoDatabase;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

/**
 * Created by matthias.popp on 11.02.2015.
 */
public interface ViewerRepository {

    /**
     * Returns the viewer for the given nick name.
     * @param nick Nick name as returned form the bot's User object.
     * @return The Viewer or null.
     */
    Viewer findViewerByNick(MongoDatabase md, String nick);

    /**
     * Returns all viewers that are currently present in the chat.
     * @return all users currently in the chat. Returns an empty list if noone is in the chat.
     */
    Set<Viewer> findCurrentViewers(MongoDatabase md);


    /**
     * Adds a viewer to the repository. If the Viewer is already present in the repository, that method overwrites
     * the old value.
     * @param md
     * @param v The viewer to add.
     */
    Viewer saveViewer(MongoDatabase md, Viewer v);

    /**
     * Updates all given viewers.
     * @param viewers viewers to update.
     */
    void saveViewers(MongoDatabase md, Set<Viewer> viewers);

    /**
     * Removes a viewer identified by the nick name. This method guarantees that after its execution the viewer with
     * the specified nick is removed from the repo.
     * @param nick The nick to remove from the repository.
     */
    void removeViewerByNick(MongoDatabase md, String nick);

    /**
     *
     *
     * @param md
     * @param nick The nick (PK) to search for in the db.
     * @return true if found, false otherwise.
     */
    boolean isViewerExisting(MongoDatabase md, String nick);



    /**
     * updates the watching state for all users.
     * @param md
     * @param watching .
     */
    void updateWatchingStateForAllUsers(MongoDatabase md, boolean watching);

    /**
     * Update a viewers watching state meaning whether the user is currently watching the stream or not.
     * @param md
     * @param nick Nickname of the user to update.
     * @param watching watching state to be set. True means the user is watching the stream.
     */
    void updateWatchingState(MongoDatabase md, String nick, boolean watching);
}
