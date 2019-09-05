package facades;

import dto.MovieDTO;
import utils.EMF_Creator;
import entities.Movie;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getMovieFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query query = em.createNativeQuery("truncate table startcode_test.MOVIE;");
        query.executeUpdate();
        em.getTransaction().commit();

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
    }

    @Test
    public void testMovieCount() {
        assertEquals(5, facade.getMovieCount());
    }

    @Test
    public void testGetAllMovieDTOs() {
        ArrayList<MovieDTO> exp = new ArrayList();
        exp.add(new MovieDTO(new Movie(2019, "Destroy the World 3", new String[]{"Boris Johnson", "Donald Trump", "Vladimir Putin", "Xi Jinping"}, 9.3)));
        exp.add(new MovieDTO(new Movie(1994, "Løvernes Konge", new String[]{"Simba", "Nala", "Mufasa", "Timon", "Pumba", "Zarsu", "Scar"}, 10.0)));
        exp.add(new MovieDTO(new Movie(1989, "Terminator", new String[]{"Arnold Schwarzenegger"}, 8.7)));
        exp.add(new MovieDTO(new Movie(1932, "Gammel Dansk", new String[]{"Ove Sprogøe", "Dirch Passer", "Poul Reichhardt", "Thomas Eje"}, 4.6)));
        exp.add(new MovieDTO(new Movie(1932, "Top Fun", new String[]{"Obama", "John Cruise", "Val Gilmour"}, 3.3)));
        assertEquals(exp, facade.getAllMoviesDTO());
    }

    @Test
    public void testGetMovieDTOByName() throws Exception {
        MovieDTO exp = new MovieDTO(new Movie(2019, "Destroy the World 3", new String[]{"Boris Johnson", "Donald Trump", "Vladimir Putin", "Xi Jinping"}, 9.3));
        assertEquals(exp, facade.getMovieDTOByName("Destroy the World 3"));
    }
}
