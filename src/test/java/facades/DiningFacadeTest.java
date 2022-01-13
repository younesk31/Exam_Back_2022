package facades;

import entities.Role;
import entities.User;
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
        facade = DiningFacade.getMovieFacade(emf);
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            user = new User("user1", "user1");
//            admin = new User("admin1", "admin1");
//            Role userRole = new Role("user");
//            Role adminRole = new Role("admin");
//            user.addRole(userRole);
//            admin.addRole(adminRole);
//            em.persist(userRole);
//            em.persist(adminRole);
//            em.persist(user);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
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
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            //user = new User("user", "user1");
            user.addRole(userRole);
            //admin = new User("admin", "admin1");
            admin.addRole(adminRole);
            //both = new User("user_admin", "user_admin1");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
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