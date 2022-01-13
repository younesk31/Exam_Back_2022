package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.bind.v2.TODO;
import dtos.AssignmentDTO;
import dtos.DinnerEventDTO;
import dtos.TransactionDTO;
import dtos.UserDTO;
import facades.DiningFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("/dining")
public class DiningResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    @Context
    private UriInfo context;
    private final DiningFacade facade = DiningFacade.getDiningFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    SecurityContext securityContext;

    // US-1
    @GET
    @Path("diningevents")
    //@RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDiningEvents() {
        List<DinnerEventDTO> dinnerEventDTOList = new ArrayList<>();
        try {
            dinnerEventDTOList = facade.getAllDiningEvents();
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(dinnerEventDTOList);
    }
    // US-1/½
//    @GET
//    @Path("mydiningevents")
//    //@RolesAllowed("user")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getMemberDiningEvents() {
//        List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
//        try {
//            String thisuser = securityContext.getUserPrincipal().getName();
//            assignmentDTOList = facade.getMemberDiningEvents(thisuser);
//        } catch (Exception e){
//            //catch something here before React does
//        }
//        return gson.toJson(assignmentDTOList);
//    }

    // US-2
    @GET
    @Path("transactions")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransactions() {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        try {
            String thisuser = securityContext.getUserPrincipal().getName();
            transactionDTOS = facade.getMemberTransactions(thisuser);

        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(transactionDTOS);
    }
    // US-2/½
    @GET
    @Path("alltransactions")
    //@RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTransactions() {
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        try {
            transactionDTOS = facade.getAllTransactions();
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(transactionDTOS);
    }

    // US-4
    //Todo mangler

//    public DinnerEvent(DinnerEventDTO dinnerEventDTO) {
//        this.eventname = dinnerEventDTO.getEventname();
//        this.time = dinnerEventDTO.getTime();
//        this.location = dinnerEventDTO.getLocation();
//        this.dish = dinnerEventDTO.getDish();
//        this.priceprperson = dinnerEventDTO.getPriceprperson();
//    }
    @POST
    @Path("createevent")
    //@RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public String createDiningEvent(String event) {
        DinnerEventDTO dinnerEventDTO = null;
        try {
            dinnerEventDTO = gson.fromJson(event, DinnerEventDTO.class);
            dinnerEventDTO = facade.createDiningEvent(dinnerEventDTO);
            System.out.println("Created a new Dining-Event: " + event);
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(dinnerEventDTO);
    }

    // US-5
    @DELETE
    @Path("removememberfromevent/{username}")
    //@RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeMemberFromEvent(@PathParam("username") String username) {
        AssignmentDTO assignmentDTO = null;
        try {
            assignmentDTO = facade.removeMemberFromAssignmentAndDiningEvent(username);
            System.out.println("User: " + username + " Removed from Dining-Event!");
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(assignmentDTO);
    }
    // US-6
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updatediningevent")
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateDiningEvent(String newDiningEvent) {
        DinnerEventDTO dinnerEventDTO = null;
        try {
        dinnerEventDTO = gson.fromJson(newDiningEvent, DinnerEventDTO.class);
        dinnerEventDTO = facade.updateDiningEvent(dinnerEventDTO);
        System.out.println("Dining-Event named: "+ dinnerEventDTO.getEventname() + " Has been Updated!");
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(dinnerEventDTO);
    }
    // US-7
    @DELETE
    @Path("removediningevent")
    //@RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDiningEvent(String event) {
        DinnerEventDTO dinnerEventDTO = null;
        try {
            dinnerEventDTO = gson.fromJson(event, DinnerEventDTO.class);
            dinnerEventDTO = facade.deleteDiningEvent(dinnerEventDTO);
            System.out.println("Dining-Event: " + event + " Removed!");
        } catch (Exception e){
            //catch something here before React does
        }
        return gson.toJson(dinnerEventDTO);
    }





}