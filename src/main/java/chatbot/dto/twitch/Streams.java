package chatbot.dto.twitch;

/**
 * @author Matthias Popp
 */
public class Streams {
    private final int _total;

    public Streams(int _total){
        this._total = _total;
    }

    public int getTotal(){
        return _total;
    }
}
