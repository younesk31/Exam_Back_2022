package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class SetupTestUsers {

  public static void main(String[] args) {
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    User user = new User("user", "user1", "address", "50101010", "mail@mail.dk", 1995,5000);
    User admin = new User("admin", "admin1","address1", "20202020", "mail@mail.sv", 2006,99999);

    Transaction t1 = new Transaction(500);
    Transaction t2 = new Transaction(1000);

    Assignment a1 = new Assignment("familie","contactinfo");

    DinnerEvent d1 = new DinnerEvent("event","11:30","konventum", "laks", 225.75);

    Role userRole = new Role("user");
    Role adminRole = new Role("admin");


    try {
      em.getTransaction().begin();

      // TEST Rolle
      user.addRole(userRole);
      admin.addRole(adminRole);
      // TEST Transaktion
      user.addTransaction(t1);
      admin.addTransaction(t2);
      // TEST Assignments
      user.addAssignments(a1);
      admin.addAssignments(a1);
      // TEST DinnerEvent
      d1.addAssignment(a1);

      em.persist(d1);
      em.persist(user);
      em.persist(admin);
      em.getTransaction().commit();

      TypedQuery<Transaction> q1 = em.createQuery("select t from Transaction t", Transaction.class);
      List<Transaction> transactions = q1.getResultList();
      for (Transaction t: transactions) {
        System.out.println(t.getUser().getUsername() + ": " + t.getTransactionAmount() + " - Account Balance: "+ t.getUser().getAccountBalance() + " - Role: "+t.getUser().getRolesAsStrings());
      }

    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}


