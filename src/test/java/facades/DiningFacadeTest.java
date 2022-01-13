package facades;

import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiningFacadeTest {

    private static EntityManagerFactory emf;
    private static DiningFacade facade;
    private static User user;
    private static User admin;
    private static User both;



    public DiningFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DiningFacade.getDiningFacade(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = new User("user", "user1", "address", "50101010", "mail@mail.dk", 1995,5000);
            User admin = new User("admin", "admin1","address1", "20202020", "mail@mail.sv", 2006,99999);

            Transaction t1 = new Transaction(500);
            Transaction t2 = new Transaction(1000);

            Assignment a1 = new Assignment("familie","contactinfo");

            DinnerEvent d1 = new DinnerEvent("event","11:30","konventum", "laks", 225.75);

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");


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
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from Transaction ").executeUpdate();
            em.createQuery("delete from Assignment ").executeUpdate();
            em.createQuery("delete from DinnerEvent ").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            User user = new User("user", "user1", "address", "50101010", "mail@mail.dk", 1995,5000);
            User admin = new User("admin", "admin1","address1", "20202020", "mail@mail.sv", 2006,99999);

            Transaction t1 = new Transaction(500);
            Transaction t2 = new Transaction(1000);

            Assignment a1 = new Assignment("familie","contactinfo");

            DinnerEvent d1 = new DinnerEvent("event","11:30","konventum", "laks", 225.75);

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");


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
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }
}