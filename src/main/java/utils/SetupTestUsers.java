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

    User user = new User("user", "user1", "fed address 123", "50101010", "mail@mail.dk", 1995,5000);
    User user1 = new User("user", "tester", "test adresse 165", "50101010", "mail@mail.dk", 1995,5000);
    User admin = new User("admin", "admin1","address1", "20202020", "mail@mail.sv", 2006,99999);
    User admin1 = new User("dev", "ax2","address1", "20202020", "mail@mail.sv", 2006,99999);

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
      user1.addRole(userRole);
      admin.addRole(adminRole);
      admin1.addRole(adminRole);
      // TEST Transaktion
      user.addTransaction(t1);
      user1.addTransaction(t2);
      admin.addTransaction(t1);
      admin1.addTransaction(t2);
      // TEST Assignments
      user.addAssignments(a1);
      user1.addAssignments(a1);
      admin.addAssignments(a1);
      admin1.addAssignments(a1);
      // TEST DinnerEvent
      d1.addAssignment(a1);

      em.persist(d1);
      em.persist(user);
      em.persist(user1);
      em.persist(admin);
      em.persist(admin1);
      em.getTransaction().commit();

    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}


