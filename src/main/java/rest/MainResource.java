package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UserDTO;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
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
}