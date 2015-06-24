package chatbot.dto;

/**
 * Dto holding calculated point increments. Contains a separate increment for session and total points since these 2
 * most likely differ.
 */
public class PointIncrement {
    private final long sessionPointIncrement;
    private final long totalPointIncrement;

    public PointIncrement(final long sessionPointIncrement, final long totalPointIncrement){
        this.sessionPointIncrement = sessionPointIncrement;
        this.totalPointIncrement = totalPointIncrement;
    }

    public long getSessionPointIncrement(){
        return sessionPointIncrement;
    }

    public long getTotalPointIncrement(){
        return totalPointIncrement;
    }

}
