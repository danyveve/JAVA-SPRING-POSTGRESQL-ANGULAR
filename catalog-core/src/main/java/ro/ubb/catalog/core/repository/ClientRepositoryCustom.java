package ro.ubb.catalog.core.repository;



import ro.ubb.catalog.core.model.Client;

import java.util.List;


public interface ClientRepositoryCustom {
    List<Client> findAllWithRentalsAndMovieJPQL();

    //List<Movie> findAllWithRentalsAndMovieCriteriaAPI();

    //List<Client> findAllWithRentalsAndMovieSQL();
}
