package com.complete.layers.test.repository;

import com.complete.layers.test.model.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created 23/10/2022 - 09:42
 * @Package com.complete.layers.test.repository
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@DisplayName("Test Movie Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Get movies list")
    @Order(1)
    public void testGetListOfMovies() {

        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        movieRepository.save(avatarMovie);

        Movie titanicMovie = new Movie();
        titanicMovie.setTitle("Titanic");
        titanicMovie.setOverview("You will love this movie");
        titanicMovie.setHomePage("www.titanic.com");
        titanicMovie.setGenera("Romance");
        titanicMovie.setRuntime(143);
        titanicMovie.setReleaseDate("25-04-1999");
        titanicMovie.setVoteAverage(7.4);
        movieRepository.save(titanicMovie);

        //Act :: When
        List<Movie> movieList = movieRepository.findAll();

        //Assert :: Then
//        assertNotNull(movieList);
        Assertions.assertThat(movieList).isNotNull();
        assertFalse(movieList.isEmpty(), "The returned movies list it's can't be empty");
        assertEquals(2, movieList.size(), "The returned movies list size is not equals");
    }

    @Test
    @DisplayName("Get movie by id")
    @Order(2)
    public void testGetMovieById_whenValidMovieIdProvided_returnMovieDetails() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        movieRepository.save(avatarMovie);

        //Act :: When
        Movie movie = movieRepository.findById(1L).get();

        //Assert :: Then
        assertNotNull(movie, "The returned movie is can't be null");
        assertEquals("Avatar", movie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals("Fantasy", movie.getGenera(), "The returned movie genera is mostly incorrect");
        assertEquals(1, movie.getId(), "The returned movie id is mostly incorrect");
    }

    @Test
    @DisplayName("Movie can be created")
    @Order(3)
    public void testCreateMovie_whenValidMovieDetailsProvided_returnsCreatedMovieDetails() {

        //Arrange :: Given
        Movie movie = new Movie();
        movie.setTitle("Avatar");
        movie.setOverview("You will love this movie");
        movie.setHomePage("www.avatar.com");
        movie.setGenera("");
        movie.setRuntime(160);
        movie.setReleaseDate("15-01-2010");
        movie.setVoteAverage(7.2);

        //Act :: When
        Movie createdMovie = movieRepository.save(movie);

        //Assert :: Then
        assertNotNull(createdMovie);
        assertThat(createdMovie.getId()).isNotNull();
        assertEquals(movie.getTitle(), createdMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals(movie.getVoteAverage(), createdMovie.getVoteAverage(), "The returned movie vote average is mostly incorrect");
    }

    @Test
    @DisplayName("Movie can be updated")
    @Order(4)
    public void testUpdateMovie_whenValidMovieDetailsProvided_returnsUpdatedMovieDetails() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy,Adventure");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        movieRepository.save(avatarMovie);

        //Act :: When
        Movie getMovie = movieRepository.findById(1L).get();
        getMovie.setTitle("Titanic");
        getMovie.setOverview("You will love this movie");
        getMovie.setHomePage("www.titanic.com");
        getMovie.setGenera("Romance");
        getMovie.setRuntime(143);
        getMovie.setReleaseDate("25-04-1999");
        getMovie.setVoteAverage(7.4);
        Movie updatedMovie = movieRepository.save(getMovie);

        //Assert :: Then
        Assertions.assertThat(updatedMovie).isNotNull();
        assertEquals("Titanic", updatedMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals("Romance", updatedMovie.getGenera(), "The returned movie genera is mostly incorrect");
    }

    @Test
    @DisplayName("Movie can be deleted")
    @Order(5)
    public void testDeletedMovie_whenValidMovieIdProvided_returnsDeletedMovieMessage() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy,Adventure");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        movieRepository.save(avatarMovie);

        //Act :: When
        Movie getMovie = movieRepository.findById(1L).get();
        movieRepository.delete(getMovie);
        Optional<Movie> fetchedMovie = movieRepository.findById(1L);

        Assertions.assertThat(fetchedMovie).isEmpty();

        NoSuchElementException throwException = assertThrows(NoSuchElementException.class, () -> {
            movieRepository.findById(1L).get();
        }, "get movie by id after deleted should have caused an No Such Element Exception with (No value present) message");

        assertEquals("No value present", throwException.getMessage(),
                "Exception error message is not correct");
    }
}
