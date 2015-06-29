package chatbot.datacollectors;

/**
 * Marker interface for dependency injection.
 */
public interface DataCollector {

    public void start();
    public void stop();
}
