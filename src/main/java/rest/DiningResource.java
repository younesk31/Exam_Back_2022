package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datamappers.DiningMapper;
import dtos.TransactionDTO;
import facades.DiningFacade;
import security.HttpClient;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/dining")
public class DiningResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private DiningMapper diningMapper = new DiningMapper();

    @Context
    private UriInfo context;
    private DiningFacade facade = DiningFacade.getMovieFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    SecurityContext securityContext;

    @GET
    @Path("transactions")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransactions() throws Exception {
        String thisuser = securityContext.getUserPrincipal().getName();
        List<TransactionDTO> transactionDTOS = facade.getTransactionList(thisuser);
        System.out.println("Username: "+ thisuser);
        return gson.toJson(transactionDTOS);
    }



}