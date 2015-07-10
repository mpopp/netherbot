package chatbot.entities;

import javax.persistence.Embeddable;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Embeddable
public class Wallet {
    public Long totalPoints;
    public Long sessionPoints;

    public Wallet(){
        totalPoints = 0L;
        sessionPoints = 0L;
    }

    public Wallet(long totalPoints, long sessionPoints){
        this.totalPoints = totalPoints;
        this.sessionPoints = sessionPoints;
    }

    @Override
    public String toString() {
        return "WALLET: T" + totalPoints + "/S" + sessionPoints;
    }
}
