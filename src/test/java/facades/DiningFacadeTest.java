package facades;

import dtos.DinnerEventDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class DiningFacadeTest {

    private static EntityManagerFactory emf;
    private static DiningFacade facade;



    public DiningFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DiningFacade.getDiningFacade(emf);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from Transaction ").executeUpdate();
            em.createQuery("delete from Assignment ").executeUpdate();
            em.createQuery("delete from DinnerEvent ").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();


            User user = new User("user", "test", "test", "123456789", "test@test.test", 1111,2222);
            User admin = new User("admin", "test","test", "987654321", "test@test.test", 1111,2222);

            Transaction t1 = new Transaction(1234);
            Transaction t2 = new Transaction(4321);

            Assignment a1 = new Assignment("test","test");

            DinnerEvent d1 = new DinnerEvent("test","03:03","test", "test", 333.33);
            DinnerEvent d2 = new DinnerEvent("test","03:03","test", "test", 333.33);


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
            em.persist(d2);
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


            User user = new User("user", "test", "test", "123456789", "test@test.test", 1111,2222);
            User admin = new User("admin", "test","test", "987654321", "test@test.test", 1111,2222);

            Transaction t1 = new Transaction(1234);
            Transaction t2 = new Transaction(4321);

            Assignment a1 = new Assignment("test","test");

            DinnerEvent d1 = new DinnerEvent("test","03:03","test", "test", 333.33);
            DinnerEvent d2 = new DinnerEvent("test","03:03","test", "test", 333.33);


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
            em.persist(d2);
            em.persist(user);
            em.persist(admin);
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