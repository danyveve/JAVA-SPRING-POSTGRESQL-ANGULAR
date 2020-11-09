package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.model.RentalPK;
import ro.ubb.catalog.core.model.validator.ClientValidator;
import ro.ubb.catalog.core.model.validator.MovieValidator;
import ro.ubb.catalog.core.model.validator.RentalValidator;
import ro.ubb.catalog.core.model.validator.ValidatorException;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieRentalServiceImpl implements MovieRentalService {
    private static final Logger log = LoggerFactory.getLogger(
            MovieRentalServiceImpl.class);


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieValidator movieValidator;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientValidator clientValidator;

    @Autowired
    private RentalValidator rentalValidator;

    @Override
    public Movie addMovie(Movie movie) throws ValidatorException {
        log.trace("saveMovie: movie={}", movie);
        try {
            movieValidator.validate(movie);
        } catch (ValidatorException e) {
            log.trace("saveMovie --- method throws ValidatorError");
            throw e;
        }
        //the saved entity will NEVER BE NULL (from documentation)
        Movie movieSaved = movieRepository.save(movie);

        log.trace("saveMovie --- method finished");
        return movieSaved;
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) throws IllegalArgumentException {
        log.trace("deleteMovie: id={}", id);

        try {
            Movie movie = this.movieRepository.findById(id).get();
            movie.getClients().stream().forEach( c -> c.getRentals().removeIf(r -> r.getMovie().getId().equals(movie.getId())));
            movie.getRentals().clear();
            movieRepository.deleteById(id);
        } catch (Exception e) {
            log.trace("deleteMovie --- exception throwed");
            throw e;
        }

        log.trace("deleteMovie --- method finished");
    }

    @Override
    @Transactional
    public Movie updateMovie(Long id, Movie movie){
        log.trace("updateMovie: movie={}", movie);

        try {
            Optional<Movie> result = movieRepository.findById(id);
            result.ifPresentOrElse(movieReceived -> {
                        movieReceived.setTitle(movie.getTitle());
                        movieReceived.setYear(movie.getYear());
                        movieReceived.setDuration(movie.getDuration());
                        movieReceived.setGenre(movie.getGenre());
                        movieReceived.setImdbRating(movie.getImdbRating());
                        movieReceived.setTrailerLink(movie.getTrailerLink());
                        log.debug("updateMovie --- movie updated --- " +
                                "movie={}", movieReceived);
                    },
                    () -> log.debug("updateMovie --- movie not found --- "));
            log.trace("updateMovie: result={}", result);
            return result.orElse(null);
        } catch (Exception e) {
            log.debug("updateMovie --- exception thrown");
            throw e;
        }
    }

    @Override
    public Movie getMovie(Long id) throws NoSuchElementException {
        log.trace("getMovie: id={}", id);
        try {
            Optional<Movie> movieOptional = movieRepository.findById(id);

            if (movieOptional.isPresent()) {
                log.trace("getMovie: movie={}", movieOptional.get());
                return movieOptional.get();
            } else
                throw new NoSuchElementException("No movie with this ID found!");
        } catch (Exception e) {
            log.debug("getMovie --- exception thrown");
            throw e;
        }
    }

    @Override
    public Set<Movie> getAllMovies() {
        log.trace("getAllMovies --- method entered");

        //List<Movie> result = movieRepository.findAll();
        //System.out.println(result.get(0).getRentals());
        //List<Movie> result = movieRepository.findAllWithRentalsAndClientJPQL();
        List<Movie> result = movieRepository.findAllWithRentalsAndClient();
        log.trace("getAllMovies: result={}", result);

        return new HashSet<>(result);
    }

    @Override
    public Set<Movie> filterByTitle(String title) {
        log.trace("filterByTitle --- method entered");
        Iterable<Movie> movieIterable = movieRepository.findAll();

        log.trace("filterByTitle: result={}", movieIterable.toString());
        return StreamSupport.stream(movieIterable.spliterator(), false)
                .filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public Client addClient(Client client) throws ValidatorException {
        log.trace("addClient: client={}", client);
        try {
            clientValidator.validate(client);
        } catch (ValidatorException e) {
            log.trace("addClient --- method throws ValidatorError");
            throw e;
        }
        //the saved entity will NEVER BE NULL (from documentation)
        Client clientSaved = clientRepository.save(client);

        log.trace("addClient --- method finished");
        return clientSaved;
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        log.trace("deleteClient: id={}", id);

        try {
            Client client = clientRepository.findById(id).get();
            client.getMovies().stream().forEach( m -> m.getRentals().removeIf(r -> r.getClient().equals(client)));
//            client.getRentals().clear();
            clientRepository.deleteById(id);
        } catch (Exception e) {
            log.trace("deleteClient --- exception throwed");
            throw e;
        }

        log.trace("deleteClient --- method finished");
    }

    @Transactional
    @Override
    public Client updateClient(Long id, Client client) {
        log.trace("updateClient: client={}", client);

        try {
            Optional<Client> result = clientRepository.findById(id);
            result.ifPresentOrElse(clientReceived -> {
                        clientReceived.setName(client.getName());
                        clientReceived.setEmail(client.getEmail());
                        clientReceived.setDateOfBirth(client.getDateOfBirth());
                        clientReceived.setDateOfRegister(client.getDateOfRegister());
                        log.debug("updateClient --- client updated --- " +
                                "client={}", clientReceived);
                    },
                    () -> log.debug("updateClient --- movie not found --- "));
            log.trace("updateClient: result={}", result);
            return result.orElse(null);
        } catch (Exception e) {
            log.debug("updateClient --- exception thrown");
            throw e;
        }
    }

    @Override
    public Client getClient(Long id) throws NoSuchElementException {
        log.trace("getClient: id={}", id);
        try {
            Optional<Client> clientOptional = clientRepository.findById(id);

            if (clientOptional.isPresent()) {
                log.trace("getClient: client={}", clientOptional.get());
                return clientOptional.get();
            } else
                throw new NoSuchElementException("No client with this ID found!");
        } catch (Exception e) {
            log.debug("getClient --- exception thrown");
            throw e;
        }
    }

    @Override
    public Set<Client> getAllClients() {
        log.trace("getAllClients --- method entered");

        //List<Client> result = clientRepository.findAll();
        List<Client> result = clientRepository.findAllWithRentalsAndMovie();

        log.trace("getAllClients: result={}", result);

        return new HashSet<>(result);
    }


    @Override
    @Transactional
    public Rental addRental(Rental rental) throws ValidatorException {
        log.trace("addRental: rental={}", rental);
        try {
            rentalValidator.validate(rental);
        } catch (ValidatorException e) {
            log.trace("addRental --- method throws ValidatorError");
            throw e;
        }
        //the saved entity will NEVER BE NULL (from documentation)
        //Rental rentalSaved = rentalRepository.save(rental);
        Client c = this.clientRepository.findAllWithRentals().stream().filter(cl -> cl.getId().equals(rental.getClient().getId())).findFirst().get();
        Movie m = this.movieRepository.findById(rental.getMovie().getId()).get();
        c.addDates(m, rental.getDateRented(), rental.getDateDue());
        log.trace("addRental --- method finished");
        return rental;
    }


    @Override
    @Transactional
    public Rental updateRental(RentalPK rentalPK, Rental rental) {
        log.trace("updateRental: rental={}", rental);

        try {
            rentalValidator.validate(rental);
            Client c = this.clientRepository.findById(rentalPK.getClient().getId()).get();
            c.updateRental(rental.getMovie(), rental.getDateRented(), rental.getDateDue());

            log.trace("updateRental: result={}", rental);
            return rental;
        } catch (Exception e) {
            log.debug("updateRental --- exception thrown");
            throw e;
        }
    }

    @Override
    @Transactional
    public Rental getRental(RentalPK rentalPK) throws NoSuchElementException {
        log.trace("getRental: rentalPK={}", rentalPK);
        try {
            //Optional<Rental> rentalOptional = rentalRepository.findById(rentalPK);
            Optional<Rental> rentalOptional = this.getAllRentals().stream()
                    .filter(rental -> rental.getClient().getId().equals(rentalPK.getClient().getId())
                            && rental.getMovie().getId().equals(rentalPK.getMovie().getId()))
                    .findFirst();
            if (rentalOptional.isPresent()) {
                log.trace("getRental: rental={}", rentalOptional.get());
                return rentalOptional.get();
            } else
                throw new NoSuchElementException("No rental with this PK found!");
        } catch (Exception e) {
            log.debug("getRental --- exception thrown");
            throw e;
        }
    }

    @Override
    @Transactional
    public Set<Rental> getAllRentals() {
        log.trace("getAllRentals --- method entered");

        Set<Rental> result = new HashSet<>();
        this.clientRepository.findAll().forEach(c -> result.addAll(c.getRentals()));

        //List<Rental> result = rentalRepository.findAll();

        log.trace("getAllRentals: result={}", result);

        return new HashSet<>(result);
    }


    @Override
    public long nrMoviesGivenGenre(String genre) {
        log.trace("nrMoviesGivenGenre --- method entered, genre={}", genre);

        long nrMovies = this.getAllMovies().stream().
                filter(movie -> movie.getGenre().toLowerCase().equals(genre.toLowerCase()))
                .count();

        log.trace("nrMoviesGivenGenre: result={}", nrMovies);
        return nrMovies;
    }

    @Override
    public List<Client> sortClientsByName() {
        log.trace("sortClientsByName --- method entered");
        List<Client> sortedClients = clientRepository.findAll().stream()
                .sorted(Comparator.comparing(client -> client.getName().toLowerCase()))
                .collect(Collectors.toList());
        log.trace("sortClientsByName: result={}", sortedClients);
        return sortedClients;
    }

    /*
    @Override
    public Movie mostRentedMovie() throws NoSuchElementException {
        //get all rentals
        Set<Rental> rentals = this.getAllRentals();
        //create a map with the key being the MOVIE_ID and the value being the NUMBER OF RENTALS of this movie
        Map<Long, Long> rentalsMap =
                rentals.stream().collect(groupingBy(Rental::getMovieID, counting()));
        //sort the entries of this map by the number of rentals in reverse order, and get the key of the first entry if it exist
        //it will be MOVIE_ID of the most rented movie
        Long movieID = rentalsMap.entrySet()
                .stream().min(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NoSuchElementException("No rentals available!"));

        return this.getMovie(movieID);
    }
    */
}
