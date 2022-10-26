package com.complete.layers.test.controller;

import com.complete.layers.test.model.Movie;
import com.complete.layers.test.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created 25/10/2022 - 12:04
 * @Package com.complete.layers.test.controller
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private IMovieService iMovieService;

    @Autowired
    public MovieController(IMovieService iMovieService) {
        this.iMovieService = iMovieService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<Movie> movieList() {
        return iMovieService.movies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Movie getMovieById(@PathVariable(name = "id") Long id) {
        return iMovieService.getMoveById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Movie saveMovie(@RequestBody Movie movie) {
        return iMovieService.save(movie);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private Movie updateMovie(@PathVariable(name = "id") Long id, @RequestBody Movie movie) {
        return iMovieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteMovie(@PathVariable(name = "id") Long id) {
        iMovieService.delete(id);
    }
}

