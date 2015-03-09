package chatbot.entities;

import org.springframework.validation.Validator;

/**
 * Created by matthias6 on 06.03.2015.
 */
public class ShopItem {
    private Validator validators;
    private int stock;
    public long price;

    public Validator getValidators() {
        return validators;
    }

    public boolean isOnStock() {
        //Explicit check for 0 because with a stock of -1 we want to express an item that is in stock infinitely
        return stock != 0;
    }

    public int getStock(){
        return stock;
    }

    /**
     * Decreases stock by one if stock is greater 0.
     * @return The new stock value.
     */
    public int decreaseStock(){
        if(stock > 0) {
            stock--;
        }
        return stock;
    }
}
