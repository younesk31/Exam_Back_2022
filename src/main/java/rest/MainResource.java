package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import entities.*;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/info")
public class MainResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @Context
    private UriInfo context;
    private final UserFacade facade = UserFacade.getUserFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }


    @GET
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        String welcome = "Welcome "+ thisuser;
        return gson.toJson(welcome);
    }

    @GET
    @Path("userdata")
    @RolesAllowed("user")
    public String getUserData() {
        String thisuser = securityContext.getUserPrincipal().getName();
        UserDTO userDTO = facade.getUserData(thisuser);
        return gson.toJson(userDTO);
    }

    @GET
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        String welcome = "Welcome "+ thisuser;
        return gson.toJson(welcome);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin/users")
    @RolesAllowed("admin")
    public String getAllUsers() {
        List<UserDTO> userDTOList = facade.getAllUsers();
        return gson.toJson(userDTOList);
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createuser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String newUser) {
        UserDTO userDTO = gson.fromJson(newUser, UserDTO.class);
        userDTO = facade.createUser(userDTO);
        System.out.println("User: "+ newUser + " Created");
        return gson.toJson(userDTO);
    }

    @DELETE
    @Path("admin/deleteuser/{name}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("name") String name) {
        UserDTO userDTO = facade.deleteUser(name);
        System.out.println("User: " + name + " Deleted");
        return gson.toJson(userDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("edituser")
    @Consumes(MediaType.APPLICATION_JSON)
    public String editUser(String newUser) {
        UserDTO userDTO = gson.fromJson(newUser, UserDTO.class);
        userDTO = facade.updateUser(userDTO);
        System.out.println("Username: "+ userDTO.getUsername()+" - Password: "+userDTO.getPassword());
        return gson.toJson(userDTO);
    }

    //Create Users on Endpoint
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createusers")
    public String createUsers() {

        EntityManager em = EMF.createEntityManager();

        User user = new User("user", "user1", "fed address 123", "50101010", "mail@mail.dk", 1995,5000);
        User user1 = new User("user1", "tester", "test adresse 165", "50101010", "mail@mail.dk", 1995,5000);
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

            return "Users Created!";
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            em.close();
        }
    }
}