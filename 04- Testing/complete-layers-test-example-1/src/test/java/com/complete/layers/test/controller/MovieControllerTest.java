package com.complete.layers.test.controller;

import com.complete.layers.test.model.Movie;
import com.complete.layers.test.service.IMovieService;
import com.complete.layers.test.service.impl.MovieServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @Created 25/10/2022 - 14:08
 * @Package com.complete.layers.test.controller
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@WebMvcTest(controllers = MovieController.class)
@MockBean({MovieServiceImpl.class})
public class MovieControllerTest {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie avatarMovie;
    private Movie titanicMovie;

    @BeforeEach
    private void init() {
        avatarMovie = new Movie();
        avatarMovie.setId(1L);
        avatarMovie.setTitle("Avatar");
        avatarMovie.setOverview("You will love this movie");
        avatarMovie.setHomePage("www.avatar.com");
        avatarMovie.setGenera("Fantasy");
        avatarMovie.setRuntime(160);
        avatarMovie.setReleaseDate("15-01-2010");
        avatarMovie.setVoteAverage(7.2);

        titanicMovie = new Movie();
        titanicMovie.setId(1L);
        titanicMovie.setTitle("Titanic");
        titanicMovie.setOverview("You will love this movie");
        titanicMovie.setHomePage("www.titanic.com");
        titanicMovie.setGenera("Romance");
        titanicMovie.setRuntime(143);
        titanicMovie.setReleaseDate("25-04-1999");
        titanicMovie.setVoteAverage(7.4);
    }

    @Test
    @DisplayName("Get movies list")
    @Order(1)
    public void testGetListOfMovies() throws Exception {

        List<Movie> movies = new ArrayList<>();
        movies.add(avatarMovie);
        movies.add(titanicMovie);

        Mockito.when(movieService.movies()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.movies").exists())
//                .andExpect(jsonPath("$.movies[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.size()", is(movies.size())));

        //This Also work
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/api/v1/movies")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
//
//        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
//
//        List<Movie> movieList = objectMapper.readValue(responseBodyAsString, new TypeReference<List<Movie>>(){});
//
//        Assertions.assertNotNull(movieList);
//        assertFalse(movieList.isEmpty(), "The returned movies list it's can't be empty");
//        assertEquals(2, movieList.size(), "The returned movies list size is not equals");
    }

    @Test
    @DisplayName("Get movie by id")
    @Order(2)
    public void testGetMovieById_whenValidMovieIdProvided_returnMovieDetails() throws Exception {

        Mockito.when(movieService.getMoveById(anyLong())).thenReturn(avatarMovie);

        mockMvc.perform(get("/api/v1/movies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(avatarMovie.getTitle())))
                .andExpect(jsonPath("$.genera", is(avatarMovie.getGenera())));
    }

    @Test
    @DisplayName("Movie can be created")
    @Order(3)
    public void testCreateMovie_whenValidMovieDetailsProvided_returnsCreatedMovieDetails() throws Exception {

        Mockito.when(movieService.save(Mockito.any(Movie.class))).thenReturn(avatarMovie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();

        Movie createdMovie = objectMapper.readValue(responseBodyAsString, Movie.class);

        assertNotNull(createdMovie);
        Assertions.assertEquals(avatarMovie.getTitle(), createdMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertThat(createdMovie.getId()).isNotNull();
        assertEquals(avatarMovie.getTitle(), createdMovie.getTitle(), "The returned movie title is mostly incorrect");
        assertEquals(avatarMovie.getVoteAverage(), createdMovie.getVoteAverage(), "The returned movie vote average is mostly incorrect");
    }

    @Test
    @DisplayName("Movie can be updated")
    @Order(4)
    public void testUpdateMovie_whenValidMovieDetailsProvided_returnsUpdatedMovieDetails() throws Exception {

        Mockito.when(movieService.update(anyLong(), Mockito.any(Movie.class))).thenReturn(avatarMovie);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/movies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avatarMovie));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();

        Movie updatedMovie = objectMapper.readValue(responseBodyAsString, Movie.class);
        assertNotNull(updatedMovie);
    }


    @Test
    @DisplayName("Movie can be deleted")
    @Order(5)
    public void testDeletedMovie_whenValidMovieIdProvided_returnsDeletedMovieMessage() throws Exception {

        Mockito.doNothing().when(movieService).delete(anyLong());

        this.mockMvc.perform(delete("/api/v1/movies/{id}", 1L))
                .andExpect(status().isNoContent());
    }


}
