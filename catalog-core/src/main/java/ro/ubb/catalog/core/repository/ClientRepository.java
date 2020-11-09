package ro.ubb.catalog.core.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientRepository extends GenericAppRepository<Client, Long> {
    @Query("select distinct c from Client c")
    @EntityGraph(value = "clientWithRentals", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentals();

    @Query("select distinct c from Client c")
    @EntityGraph(value = "clientWithRentalsAndMovie", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithRentalsAndMovie();
}
