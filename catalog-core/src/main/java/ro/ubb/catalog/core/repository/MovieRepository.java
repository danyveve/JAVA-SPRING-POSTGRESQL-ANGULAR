package ro.ubb.catalog.core.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieRepository extends GenericAppRepository<Movie, Long>, MovieRepositoryCustom {
    @Query("select distinct m from Movie m")
    @EntityGraph(value = "movieWithRentals", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithRentals();

    @Query("select distinct m from Movie m")
    @EntityGraph(value = "movieWithRentalsAndClient", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithRentalsAndClient();
}
