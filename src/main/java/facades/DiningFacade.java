package facades;

import dtos.AssignmentDTO;
import dtos.DinnerEventDTO;
import dtos.TransactionDTO;
import entities.Assignment;
import entities.DinnerEvent;
import entities.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class DiningFacade {


    private static EntityManagerFactory emf;
    private static DiningFacade facade;


    private DiningFacade() {
    }


    public static DiningFacade getDiningFacade(EntityManagerFactory _emf) {
        if (facade == null) {
            emf = _emf;
            facade = new DiningFacade();
        }
        return facade;
    }

    // US-1
    public List<DinnerEventDTO> getAllDiningEvents() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<DinnerEvent> query = em.createQuery("SELECT d FROM DinnerEvent d", DinnerEvent.class);
            List<DinnerEvent> dinnerEvents = query.getResultList();
            em.getTransaction().commit();
            List<DinnerEventDTO> dinnerEventDTOList = DinnerEventDTO.getDtos(dinnerEvents);
            return dinnerEventDTOList;
        } finally {
            em.close();
        }
    }
    // US-1/½
//    public List<AssignmentDTO> getMemberDiningEvents(String user) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            TypedQuery<Assignment> query = em.createQuery("select a from Assignment a join a.users u where u.username = :user", Assignment.class);
//            query.setParameter("user", user);
//            List<Assignment> assignments = query.getResultList();
//
//            System.out.println(user);
//            System.out.println(assignments);
//
//            em.getTransaction().commit();
//            return AssignmentDTO.getDtos(assignments);
//        } finally {
//            em.close();
//        }
//    }

    // US-2
    public List<TransactionDTO> getMemberTransactions(String user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.user.username = :user ", Transaction.class);
            query.setParameter("user", user);
            List<Transaction> transactions = query.getResultList();
            em.getTransaction().commit();
            return TransactionDTO.getDtos(transactions);
        } finally {
            em.close();
        }
    }

    public List<TransactionDTO> getAllTransactions() throws Exception {
        EntityManager em = emf.createEntityManager();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<Transaction> transactionList;
        try {
            em.getTransaction().begin();
            TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);
            query.setMaxResults(10);
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



    // US-4
    public DinnerEventDTO createDiningEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = emf.createEntityManager();
        DinnerEvent dinnerEvent = new DinnerEvent(dinnerEventDTO);
        try {
            em.getTransaction().begin();
            em.persist(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    // US-5
    public AssignmentDTO removeMemberFromAssignmentAndDiningEvent(String userName) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Assignment assignment = em.find(Assignment.class, userName);
            em.remove(assignment);
            em.getTransaction().commit();
            return new AssignmentDTO(assignment);
        } finally {
            em.close();
        }
    }

    // US-6
    public DinnerEventDTO updateDiningEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class, dinnerEventDTO.getId());

            // Lidt bøvlet måske vis jeg var klar i hovedet kunne jeg komme på en mindre
            // copy/paste måde at gøre det her på....
            dinnerEvent.setEventname(dinnerEventDTO.getEventname());
            dinnerEvent.setTime(dinnerEventDTO.getTime());
            dinnerEvent.setLocation(dinnerEventDTO.getLocation());
            dinnerEvent.setDish(dinnerEventDTO.getDish());
            dinnerEvent.setPriceprperson(dinnerEventDTO.getPriceprperson());


            em.merge(dinnerEvent);
            em.getTransaction().commit();
            return new DinnerEventDTO(dinnerEvent);
        } finally {
            em.close();
        }
    }

    // US-7
    public DinnerEventDTO deleteDiningEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class, dinnerEventDTO.getId());
            Assignment assignment = em.find(Assignment.class, dinnerEventDTO.getId());
            em.remove(assignment);
            em.remove(dinnerEvent);
            em.getTransaction().commit();
            return new DinnerEventDTO(dinnerEvent);
        } finally {
            em.close();
        }
    }

}
