package rest;

import entities.Movie;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//@Disabled
public class MovieResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static final String TEST_DB = "jdbc:mysql://localhost:3307/startcode_test";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
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
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/imdb").then().statusCode(200);
    }

    @Test
    public void testRoot() throws Exception {
        given()
                .contentType("application/json").when()
                .get("/imdb").then().assertThat().
                statusCode(HttpStatus.OK_200.getStatusCode())
                .body("Welcome", equalTo("To the International Movie Database"));
    }

    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/imdb/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("moviecount", equalTo(5));
    }

    @Test
    public void testAllMovies() throws Exception {
        given()
                .contentType("application/json")
                .get("/imdb/allMovies").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("[0].name", equalTo("Destroy the World 3"))
                .body("[1].name", equalTo("Løvernes Konge"))
                .body("[2].name", equalTo("Terminator"))
                .body("[3].name", equalTo("Gammel Dansk"))
                .body("[4].name", equalTo("Top Fun"))
                .body("size()", is(5));
    }

    @Test
    public void testOneMovie() throws Exception {
        given()
                .contentType("application/json")
                .get("/imdb/name/Destroy the World 3").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("Destroy the World 3"))
                .body("releaseYear", equalTo(2019))
                .body("rating", is(9.3f))
                .body("actors", contains("Boris Johnson", "Donald Trump", "Vladimir Putin", "Xi Jinping"));
    }
}
