package chatbot.services;

import chatbot.entities.ShopItem;
import chatbot.entities.Viewer;
import chatbot.repositories.api.ShopRepository;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.utils.PersistenceUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Created by matthias6 on 06.03.2015.
 */
public class ShoppingService {

    private final ShopRepository shopRepository;
    private final ValidationService validationService;
    private final PersistenceUtils persistenceUtils;
    private final ViewerRepository viewerRepository;

    @Autowired
    public ShoppingService(PersistenceUtils persistenceUtils, ValidationService validationService, ShopRepository shopRepository, ViewerRepository viewerRepository){
        this.shopRepository = shopRepository;
        this.validationService = validationService;
        this.persistenceUtils = persistenceUtils;
        this.viewerRepository = viewerRepository;
    }

    public <T extends ShopItem> T buyItem(Viewer buyer, String itemName){
        EntityManager em = persistenceUtils.openEm();
        ShopItem item = shopRepository.findItemByname(em, itemName);
        T result = null;
        if(item.isOnStock() && buyerHasEnoughPoints(buyer, item)){
            persistenceUtils.startTransaction(em);
            item.decreaseStock();
            buyer.wallet.totalPoints = buyer.wallet.totalPoints - item.price;
            shopRepository.save(em, item);
            viewerRepository.saveViewer(em, buyer);
            persistenceUtils.commitTransaction(em);
            result = (T) item;
        }
        persistenceUtils.closeEm(em);
        return result;
    }

    private boolean buyerHasEnoughPoints(Viewer buyer, ShopItem item) {
        return item.price <= buyer.wallet.totalPoints;
    }
}
