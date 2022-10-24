package com.complete.layers.test.repository;

import com.complete.layers.test.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 23/10/2022 - 09:33
 * @Package com.complete.layers.test.repository
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
