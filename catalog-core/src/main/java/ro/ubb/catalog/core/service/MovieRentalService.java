package ro.ubb.catalog.core.service;





import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.model.RentalPK;
import ro.ubb.catalog.core.model.validator.ValidatorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface MovieRentalService {
    Movie addMovie(Movie movie) throws ValidatorException;

    void deleteMovie(Long id) throws IllegalArgumentException;

    Movie updateMovie(Long id, Movie movie);

    Movie getMovie(Long id) throws NoSuchElementException;

    Set<Movie> getAllMovies();

    Set<Movie> filterByTitle(String title);

    Client addClient(Client client) throws ValidatorException;

    void deleteClient(Long id) throws IllegalArgumentException;

    Client updateClient(Long id, Client client) throws Exception;

    Client getClient(Long id) throws NoSuchElementException;

    Set<Client> getAllClients();

    Rental addRental(Rental rental) throws ValidatorException;

    //void deleteRental(RentalPK rentalPK);

    Rental updateRental(RentalPK rentalPK, Rental rental);

    Rental getRental(RentalPK rentalPK) throws NoSuchElementException;

    Set<Rental> getAllRentals();


    long nrMoviesGivenGenre(String genre);

    List<Client> sortClientsByName();

    /*
    Movie mostRentedMovie() throws NoSuchElementException;
    */
}
