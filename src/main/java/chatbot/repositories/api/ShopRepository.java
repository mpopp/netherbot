package chatbot.repositories.api;

import chatbot.entities.ShopItem;

import javax.persistence.EntityManager;

/**
 * Created by matthias6 on 06.03.2015.
 */
public interface ShopRepository {
    ShopItem findItemByname(EntityManager em, String itemName);

    ShopItem save(EntityManager em, ShopItem item);
}
