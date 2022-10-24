package com.complete.layers.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Created 23/10/2022 - 09:06
 * @Package com.complete.layers.test.model
 * @Project complete-layers-test-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "overview")
    private String overview;
    @Column(name = "home_page")
    private String homePage;
    @Column(name = "genera")
    private String genera;
    @Column(name = "runtime")
    private Integer runtime;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "vote_average")
    private Double voteAverage;
}
