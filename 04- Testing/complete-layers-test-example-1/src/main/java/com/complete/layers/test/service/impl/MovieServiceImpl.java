package com.complete.layers.test.service.impl;

import com.complete.layers.test.model.Movie;
import com.complete.layers.test.repository.MovieRepository;
import com.complete.layers.test.service.IMovieService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Created 24/10/2022 - 11:43
 * @Package com.complete.layers.test.service.impl
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Transactional
@Slf4j
@Service
public class MovieServiceImpl implements IMovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> movies() {
        log.info("Get a list of movies");
        return movieRepository.findAll();
    }

    @Override
    public Movie getMoveById(Long id) {
        Movie getMovie = movieRepository.findById(id).get();
        if (Objects.isNull(getMovie)) {
            log.error("Movie not found by provided id: " + id);
            throw new RuntimeException("Movie not found by provided id: " + id);
        }
        log.info("Movie has been fetch successfully with title: " + getMovie.getTitle());
        return getMovie;
    }

    @Override
    public Movie save(Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        log.info("Movie has been saved successfully with title: " + savedMovie.getTitle());
        return savedMovie;
    }

    @Override
    public Movie update(Long id, Movie movie) {
        Movie getMovie = getMoveById(id);
        getMovie.setId(getMovie.getId());
        getMovie.setTitle(getMovie.getTitle());
        getMovie.setOverview(getMovie.getOverview());
        getMovie.setHomePage(getMovie.getHomePage());
        getMovie.setGenera(getMovie.getGenera());
        getMovie.setRuntime(getMovie.getRuntime());
        getMovie.setReleaseDate(getMovie.getReleaseDate());
        getMovie.setVoteAverage(getMovie.getVoteAverage());

        Movie updatedMovie = movieRepository.save(getMovie);
        log.info("Movie has been updated successfully with title: " + updatedMovie.getTitle());

        return updatedMovie;
    }

    @Override
    public void delete(Long id) {
        Movie getMovie = getMoveById(id);
        movieRepository.delete(getMovie);
        log.info("Movie has been deleted successfully with id: " + id);
    }
}
