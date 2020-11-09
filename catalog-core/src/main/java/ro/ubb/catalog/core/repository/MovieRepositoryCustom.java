package ro.ubb.catalog.core.repository;


import ro.ubb.catalog.core.model.Movie;

import java.util.List;


public interface MovieRepositoryCustom {
    List<Movie> findAllWithRentalsAndClientJPQL();

    //List<Movie> findAllWithRentalsAndClientCriteriaAPI();

    //List<Movie> findAllWithRentalsAndClientSQL();
}
