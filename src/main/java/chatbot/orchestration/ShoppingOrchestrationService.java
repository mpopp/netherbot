package chatbot.orchestration;

import chatbot.entities.ShopItem;
import chatbot.entities.Viewer;
import chatbot.repositories.utils.PersistenceUtils;
import chatbot.services.ShoppingService;
import chatbot.services.ViewerService;

import javax.persistence.EntityManager;

/**
 * Created by matthias on 09.03.2015.
 */
public class ShoppingOrchestrationService {

    private final ShoppingService shoppingService;
    private final ViewerService viewerService;
    private final PersistenceUtils persistenceUtils;

    public ShoppingOrchestrationService(ShoppingService shoppingService, ViewerService viewerService, PersistenceUtils persistenceUtils){

        this.shoppingService = shoppingService;
        this.viewerService = viewerService;
        this.persistenceUtils = persistenceUtils;
    }

    public boolean buyItem(String nick, String itemName) {
        EntityManager em = persistenceUtils.startTransaction();
        Viewer buyer = viewerService.findViewerByNick(em, nick);
        ShopItem boughtItem = shoppingService.buyItem(buyer, itemName);
        persistenceUtils.commitTransactionAndCloseEM(em);
        return boughtItem != null;
    }
}
