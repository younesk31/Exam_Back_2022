package rest;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import dtos.DinnerEventDTO;
import dtos.TransactionDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiningResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.createQuery("delete from Transaction ").executeUpdate();
            em.createQuery("delete from Assignment ").executeUpdate();
            em.createQuery("delete from DinnerEvent ").executeUpdate();

            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            em.getTransaction().commit();
        }finally {
            em.close();
        }


        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
//        System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    void getTransactions() {
        login("user","test");

        List<TransactionDTO> transactionDTOS;
        transactionDTOS = given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/dining/transactions")
                .then().statusCode(200)
                .extract().body().jsonPath().getList("", TransactionDTO.class);

        assertNotNull(transactionDTOS.size());
        assertTrue(transactionDTOS.size() >= 1);
    }


    @Test
    void createDiningEvent() {
        login("admin","test");

        JSONObject requestParams = new JSONObject();
        requestParams.put("eventname","TEST");
        requestParams.put("time","13:30");
        requestParams.put("location","ARENA");
        requestParams.put("dish","smørbrød");
        requestParams.put("priceprperson",250);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(requestParams.toString())
                .when()
                .post("/dining/createevent")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void createDiningEventBuiltToFail() {
        login("user","test");

        JSONObject requestParams = new JSONObject();
        requestParams.put("eventname","TEST");
        requestParams.put("time","13:30");
        requestParams.put("location","ARENA");
        requestParams.put("dish","smørbrød");
        requestParams.put("priceprperson",250);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(requestParams.toString())
                .when()
                .post("/dining/createevent")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED_401.getStatusCode());
    }

    @Test
    void removeMemberFromEvent() {
        login("admin","test");

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .delete("/dining/removememberfromevent/user")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void updateDiningEvent() {
        login("user","test");

        JSONObject requestParams = new JSONObject();
        requestParams.put("eventname","TEST");
        requestParams.put("time","13:30");
        requestParams.put("location","ARENA");
        requestParams.put("dish","smørbrød");
        requestParams.put("priceprperson",9000);

        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .body(requestParams.toString())
                .when()
                .put("/dining/updatediningevent/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }


}