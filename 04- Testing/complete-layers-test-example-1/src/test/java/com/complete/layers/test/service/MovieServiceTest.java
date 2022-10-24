package com.complete.layers.test.service;

import com.complete.layers.test.model.Movie;
import com.complete.layers.test.repository.MovieRepository;
import com.complete.layers.test.service.impl.MovieServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created 24/10/2022 - 13:32
 * @Package com.complete.layers.test.service
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@ExtendWith(MockitoExtension.class)
@DisplayName("Test Movie Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Should return list of movies with size 2")
    @Order(1)
    public void testGetListOfMovies() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);

        Movie titanicMovie = new Movie();
        titanicMovie.setTitle("Titanic");
        titanicMovie.setOverview("You will love this movie");
        titanicMovie.setHomePage("www.titanic.com");
        titanicMovie.setGenera("Romance");
        titanicMovie.setRuntime(143);
        titanicMovie.setReleaseDate("25-04-1999");
        titanicMovie.setVoteAverage(7.4);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(avatarMovie);
        movieList.add(titanicMovie);

        Mockito.when(movieRepository.findAll()).thenReturn(movieList);

        //Act :: When
        List<Movie> movies = movieService.movies();

        //Assert :: Then
        Assertions.assertThat(movies).isNotNull();
        assertFalse(movies.isEmpty(), "The returned movies list it's can't be empty");
        assertEquals(2, movies.size(), "The returned movies list size is not equals");
    }

    @Test
    @DisplayName("Should return a specified movie object")
    @Order(2)
    public void testGetMovieById_whenValidMovieIdProvided_returnMovieDetails() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);
        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.of(avatarMovie));

        //Act :: When
        Movie movie = movieService.getMoveById(1L);

        //Assert :: Then
        assertNotNull(movie, "The returned movie is can't be null");
        assertEquals("Avatar", movie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals("Fantasy", movie.getGenera(), "The returned movie genera is mostly incorrect");
        assertEquals(1, movie.getId(), "The returned movie id is mostly incorrect");

        assertThrows(RuntimeException.class, () -> {
            movieService.getMoveById(2L);
        });
    }

    @Test
    @DisplayName("Should save the movie object to database")
    @Order(3)
    public void testCreateMovie_whenValidMovieDetailsProvided_returnsCreatedMovieDetails() {

        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);

        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(avatarMovie);

        //Act :: When
        Movie createdMovie = movieService.save(avatarMovie);

        //Assert :: Then
        assertNotNull(createdMovie);
        assertThat(createdMovie.getId()).isNotNull();
        assertEquals(avatarMovie.getTitle(), createdMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals(avatarMovie.getVoteAverage(), createdMovie.getVoteAverage(), "The returned movie vote average is mostly incorrect");
    }

    @Test
    @DisplayName("Should updated a specified object to database")
    @Order(4)
    public void testUpdateMovie_whenValidMovieDetailsProvided_returnsUpdatedMovieDetails() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);

        Mockito.when(movieRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(avatarMovie));
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(avatarMovie);

        //Act :: When

        Movie updatedMovie = movieService.update(1L, avatarMovie);

        //Assert :: Then
        Assertions.assertThat(updatedMovie).isNotNull();
        assertEquals("Avatar", updatedMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals("Fantasy", updatedMovie.getGenera(), "The returned movie genera is mostly incorrect");
    }

    @Test
    @DisplayName("Movie can be deleted")
    @Order(5)
    public void testDeletedMovie_whenValidMovieIdProvided_returnsDeletedMovieMessage() {
        //Arrange :: Given
        Movie avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);

        Mockito.when(movieRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(avatarMovie));
        Mockito.doNothing().when(movieRepository).delete(Mockito.any(Movie.class));

        //Act :: When
        movieService.delete(1L);

        //Assert :: Then
        Mockito.verify(movieRepository, Mockito.times(1)).delete(avatarMovie);
    }
}
