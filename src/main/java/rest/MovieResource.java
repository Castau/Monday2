package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("imdb")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/data")
    public String data() {
        EntityManager em = EMF.createEntityManager();
        ArrayList<Movie> allMovies = new ArrayList();
        allMovies.add(new Movie(2019, "Destroy the World 3", new String[]{"Boris Johnson", "Donald Trump", "Vladimir Putin", "Xi Jinping"}, 9.3));
        allMovies.add(new Movie(1994, "Løvernes Konge", new String[]{"Simba", "Nala", "Mufasa", "Timon", "Pumba", "Zarsu", "Scar"}, 10.0));
        allMovies.add(new Movie(1989, "Terminator", new String[]{"Arnold Schwarzenegger"}, 8.7));
        allMovies.add(new Movie(1932, "Gammel Dansk", new String[]{"Ove Sprogøe", "Dirch Passer", "Poul Reichhardt", "Thomas Eje"}, 4.6));
        allMovies.add(new Movie(1932, "Top Fun", new String[]{"Obama", "John Cruise", "Val Gilmour"}, 3.3));
        try {
            for (Movie m : allMovies) {
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return "Movies created";
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String welcome() {
        return "{\"Welcome\":\"To the International Movie Database\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String moviecount() {
        long count = FACADE.getMovieCount();
        return "{\"moviecount\":" + count + "}";
    }

    @Path("/allMovies")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() {
        return GSON.toJson(FACADE.getAllMoviesDTO());
    }

    @Path("/name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieByName(@PathParam("name") String name) {
        try {
            return GSON.toJson(FACADE.getMovieDTOByName(name));
        } catch (Exception ex) {
            return "{\"ERROR\":\" Movie not found\"}";
        }
    }
    
    @Path("/id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") long id) {
        try {
            return GSON.toJson(FACADE.getMovieDTOById(id));
        } catch (Exception ex) {
            return "{\"ERROR\":\" Movie not found\"}";
        }
    }
//
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void create(Movie entity) {
//        throw new UnsupportedOperationException();
//    }
//
//    @PUT
//    @Path("/{id}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public void update(Movie entity, @PathParam("id") int id) {
//        throw new UnsupportedOperationException();
//    }
}
