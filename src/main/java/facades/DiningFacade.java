package facades;

import datamappers.DiningMapper;
import dtos.TransactionDTO;
import entities.Assignment;
import entities.Transaction;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DiningFacade {


    private static EntityManagerFactory emf;
    private static DiningFacade facade;


    private DiningFacade() {
    }


    public static DiningFacade getMovieFacade(EntityManagerFactory _emf) {
        if (facade == null) {
            emf = _emf;
            facade = new DiningFacade();
        }
        return facade;
    }

    public List<TransactionDTO> getTransactionList(String user) throws Exception {
        EntityManager em = emf.createEntityManager();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<Transaction> transactionList;
        try {
            em.getTransaction().begin();
            TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.user.username = :user ", Transaction.class);
            query.setParameter("user", user);
            //query.setMaxResults(10);
            transactionList = query.getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        for (Transaction t : transactionList) {
            transactionDTOList.add(new TransactionDTO(t));
        }

        return transactionDTOList;
    }


}
