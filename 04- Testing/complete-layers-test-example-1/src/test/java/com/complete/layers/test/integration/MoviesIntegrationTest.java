package com.complete.layers.test.integration;

import com.complete.layers.test.model.Movie;
import com.complete.layers.test.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Created 26/10/2022 - 10:57
 * @Package com.complete.layers.test.integration
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesIntegrationTest {

    @LocalServerPort
    private int port;
    private Movie avatarMovie;
    private Movie titanicMovie;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void init() {
        baseUrl = baseUrl + ":" + port + "/api/v1/movies";

        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        avatarMovie = movieRepository.save(avatarMovie);

        titanicMovie = new Movie();
        titanicMovie.setTitle("Titanic");
        titanicMovie.setOverview("You will love this movie");
        titanicMovie.setHomePage("www.titanic.com");
        titanicMovie.setGenera("Romance");
        titanicMovie.setRuntime(143);
        titanicMovie.setReleaseDate("25-04-1999");
        titanicMovie.setVoteAverage(7.4);
        titanicMovie = movieRepository.save(titanicMovie);
    }

    @AfterEach
    public void cleanup() {
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("should get movies")
    @Order(1)
    public void shouldGetListMovies() {
        //restTemplate.postForObject(baseUrl,avatarMovie,Movie.class);
        //restTemplate.postForObject(baseUrl,titanicMovie,Movie.class);

        List<Movie> movieList = restTemplate.getForObject(baseUrl, List.class);
        assertThat(movieList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("should get movie object By Id")
    @Order(2)
    public void shouldGetMovieById() {
//        restTemplate.postForObject(baseUrl,avatarMovie,Movie.class);

//        Movie movie = restTemplate.getForObject(baseUrl + "/1", Movie.class);
        Movie movie = restTemplate.getForObject(baseUrl + "/" + avatarMovie.getId(), Movie.class);

        assertNotNull(movie);
        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getTitle()).isEqualTo("Avatar");
        assertThat(movie.getGenera()).isEqualTo("Fantasy");
    }

    @Test
    @DisplayName("should create movie object")
    @Order(3)
    public void shouldCreateMovie() {
        Movie hangoverMovie = new Movie();
        hangoverMovie.setTitle("Hangover");
        hangoverMovie.setGenera("Comedy");

        Movie movie = restTemplate.postForObject(baseUrl, hangoverMovie, Movie.class);

        assertNotNull(movie);
        assertThat(movie.getId()).isNotNull();
        assertThat(movie.getTitle()).isEqualTo("Hangover");
        assertThat(movie.getGenera()).isEqualTo("Comedy");
    }

    @Test
    @DisplayName("should update movie object By Id")
    @Order(4)
    public void shouldUpdateMovie() {

        //avatarMovie = restTemplate.postForObject(baseUrl, avatarMovie, Movie.class);
        avatarMovie.setGenera("Fantasy Movie");

        restTemplate.put(baseUrl + "/{id}", avatarMovie, avatarMovie.getId());

        Movie existingMovie = restTemplate.getForObject(baseUrl + "/" + avatarMovie.getId(), Movie.class);

        assertNotNull(existingMovie);
        assertThat(existingMovie.getId()).isNotNull();
        assertThat(existingMovie.getTitle()).isEqualTo("Avatar");
        assertThat(existingMovie.getGenera()).isEqualTo("Fantasy Movie");
    }

    @Test
    @DisplayName("should delete movie object By Id")
    @Order(5)
    public void shouldDeleteMovieById() {
//        restTemplate.postForObject(baseUrl,avatarMovie,Movie.class);

//        Movie movie = restTemplate.getForObject(baseUrl + "/1", Movie.class);
        restTemplate.delete(baseUrl + "/" + avatarMovie.getId());

        long count = movieRepository.count();

        assertThat(count).isEqualTo(1);
    }
}
