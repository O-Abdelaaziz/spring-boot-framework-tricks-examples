package com.complete.layers.test.service;

import com.complete.layers.test.model.Movie;

import java.util.List;

/**
 * @Created 24/10/2022 - 11:35
 * @Package com.complete.layers.test.service
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IMovieService {

    public List<Movie> movies();

    public Movie getMoveById(Long id);

    public Movie save(Movie movie);

    public Movie update(Long id, Movie movie);

    public void delete(Long id);
}
