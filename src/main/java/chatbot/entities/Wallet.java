package chatbot.entities;

import javax.persistence.Embeddable;

/**
 * Created by matthias.popp on 25.02.2015.
 */
@Embeddable
public class Wallet {
    public Long totalPoints;
    public Long sessionPoints;

}
