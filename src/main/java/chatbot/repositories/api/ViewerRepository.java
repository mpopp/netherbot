package chatbot.repositories.api;

import chatbot.entities.Viewer;
import org.mongodb.morphia.Datastore;

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
    Viewer findViewerByNick(Datastore ds, String nick);

    /**
     * Returns all viewers that are currently present in the chat.
     * @return all users currently in the chat. Returns an empty list if noone is in the chat.
     */
    Set<Viewer> findCurrentViewers(Datastore ds);


    /**
     * Adds a viewer to the repository. If the Viewer is already present in the repository, that method overwrites
     * the old value.
     * @param ds
     * @param v The viewer to add.
     */
    Viewer saveViewer(Datastore ds, Viewer v);

    /**
     * Updates all given viewers.
     * @param viewers viewers to update.
     */
    void saveViewers(Datastore ds, Set<Viewer> viewers);

    /**
     * Removes a viewer identified by the nick name. This method guarantees that after its execution the viewer with
     * the specified nick is removed from the repo.
     * @param nick The nick to remove from the repository.
     */
    void removeViewerByNick(Datastore ds, String nick);

    /**
     *
     *
     * @param ds
     * @param nick The nick (PK) to search for in the db.
     * @return true if found, false otherwise.
     */
    boolean isViewerExisting(Datastore ds, String nick);



    /**
     * updates the watching state for all users.
     * @param ds
     * @param watching .
     */
    void updateWatchingStateForAllUsers(Datastore ds, boolean watching);

    /**
     * Update a viewers watching state meaning whether the user is currently watching the stream or not.
     * @param ds
     * @param nick Nickname of the user to update.
     * @param watching watching state to be set. True means the user is watching the stream.
     */
    void updateWatchingState(Datastore ds, String nick, boolean watching);
}
