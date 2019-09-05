package facades;

import dto.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    private MovieFacade() {
    }

    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
       
    public long getMovieCount() {
        EntityManager em = getEntityManager();
        try {
            long movieCount = (long) em.createNamedQuery("Movie.count").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    
    public List<MovieDTO> getAllMoviesDTO() {
        EntityManager em = getEntityManager();
        try {
            List<Movie> movies = em.createNamedQuery("Movie.getAllMovies").getResultList();
            List<MovieDTO> result = new ArrayList<>();
            movies.forEach((movie) -> {
                result.add(new MovieDTO(movie));
            });
            return result;
        } finally {
            em.close();
        }
    }

    public MovieDTO getMovieDTOByName(String name) throws Exception {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT new dto.MovieDTO(m) FROM Movie m WHERE m.name = :name", MovieDTO.class).setParameter("name", name).getSingleResult();
        } finally {
            em.close();
        }
    }

}
