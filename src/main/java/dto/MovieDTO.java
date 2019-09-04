package dto;

import entities.Movie;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Camilla
 */
public class MovieDTO {

    public MovieDTO() {
    }

    private String name; 
    private int releaseYear;
    private String[] actors;
    private double rating;
    
    public MovieDTO(Movie movie) {
        this.name = movie.getName();
        this.releaseYear = movie.getReleaseYear();
        this.actors = movie.getActors();
        this.rating = movie.getRating();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + this.releaseYear;
        hash = 13 * hash + Arrays.deepHashCode(this.actors);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieDTO other = (MovieDTO) obj;
        if (this.releaseYear != other.releaseYear) {
            return false;
        }
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Arrays.deepEquals(this.actors, other.actors)) {
            return false;
        }
        return true;
    }
    
    

}
