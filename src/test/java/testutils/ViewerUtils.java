package testutils;

import chatbot.entities.Viewer;
import chatbot.entities.Wallet;
import chatbot.factories.ViewerFactory;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Utils methods for testing viewer related operations and services
 * @author Matthias Popp
 */
public class ViewerUtils {

    public static Set<Viewer> getTenViewers(){
        return Sets.newHashSet(
                ViewerFactory.build().withNick("viewer1").withWallet(new Wallet(100, 0)).create(),
                ViewerFactory.build().withNick("viewer2").withWallet(new Wallet(100, 10)).create(),
                ViewerFactory.build().withNick("viewer3").withWallet(new Wallet(100, 20)).create(),
                ViewerFactory.build().withNick("viewer4").withWallet(new Wallet(100, 30)).create(),
                ViewerFactory.build().withNick("viewer5").withWallet(new Wallet(100, 40)).create(),
                ViewerFactory.build().withNick("viewer6").withWallet(new Wallet(100, 50)).create(),
                ViewerFactory.build().withNick("viewer7").withWallet(new Wallet(100, 60)).create(),
                ViewerFactory.build().withNick("viewer8").withWallet(new Wallet(100, 70)).create(),
                ViewerFactory.build().withNick("viewer9").withWallet(new Wallet(100, 80)).create(),
                ViewerFactory.build().withNick("viewer10").withWallet(new Wallet(100, 90)).create()
        );
    }
}
