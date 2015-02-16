package chatbot.repositories.api;

import chatbot.entities.Viewer;

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
    Viewer findViewerByNick(String nick);

    /**
     * Returns all viewers that are currently present in the chat.
     * @return all users currently in the chat. Returns an empty list if noone is in the chat.
     */
    Set<Viewer> findCurrentViewers();

    /**
     * Adds a viewer to the repository. If the Viewer is already present in the repository, that method overwrites
     * the old value.
     * @param v The viewer to add.
     */
    void addViewer(Viewer v);

    /**
     * Removes a viewer identified by the nick name. This method guarantees that after its execution the viewer with
     * the specified nick is removed from the repo.
     * @param nick The nick to remove from the repository.
     */
    void removeViewerByNick(String nick);
}
