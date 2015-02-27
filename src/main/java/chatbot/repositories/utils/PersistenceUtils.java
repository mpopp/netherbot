package chatbot.repositories.utils;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by matthias.popp on 18.07.2014.
 * <p/>
 * Class containing needed perisistence helper methods. This class also creates and manages the entity manager instance.
 */
@Component
public class PersistenceUtils {

    private final EntityManagerFactory emf;
    /**
     * No instances should be created.
     */
    public PersistenceUtils(){
        emf = Persistence.createEntityManagerFactory("netherbot");
    }

    public PersistenceUtils(String persistenceUnitName){
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public EntityManager openEm() {
        return emf.createEntityManager();
    }

    public  void closeEm(EntityManager em) {em.close();}

    public void startTransaction(EntityManager em){
        em.getTransaction().begin();
    }

    public void commitTransaction(EntityManager em){
        em.getTransaction().commit();
    }

    public boolean isTransactionActive(EntityManager em){
        return em.getTransaction().isActive();
    }

	public void rollbackTransaction(EntityManager em) {
		em.getTransaction().rollback();
	}
}
