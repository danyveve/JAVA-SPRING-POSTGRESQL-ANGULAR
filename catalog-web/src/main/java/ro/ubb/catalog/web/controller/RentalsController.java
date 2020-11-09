package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.service.MovieRentalService;
import ro.ubb.catalog.core.service.UserService;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.converter.RentalConverter;
import ro.ubb.catalog.web.converter.UserConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.RentalDto;
import ro.ubb.catalog.web.dto.UserDto;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/api")
public class RentalsController {
    private static final Logger log =
            LoggerFactory.getLogger(RentalsController.class);

    @Autowired
    MovieRentalService movieRentalService;

    @Autowired
    MovieConverter movieConverter;

    @Autowired
    ClientConverter clientConverter;

    @Autowired
    RentalConverter rentalConverter;

    @Autowired
    UserService userService;

    @Autowired
    UserConverter userConverter;


    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<MovieDto> saveMovie(@RequestBody MovieDto movieDto) {
        log.trace("saveMovie: movieDto={}", movieDto);

        Movie movieSaved = this.movieRentalService.addMovie(
                movieConverter.convertDtoToModel(movieDto));
        MovieDto result = movieConverter.convertModelToDto(movieSaved);

        log.trace("saveStudent: result={}", result);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        log.trace("deleteMovie: id={}", id);

        this.movieRentalService.deleteMovie(id);

        log.trace("deleteMovie --- method finished");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    @ResponseBody
    ResponseEntity<MovieDto> updateMovie(@PathVariable final Long id,
                                         @RequestBody final MovieDto movieDto) {
        log.trace("updateMovie: id={}, movieDto={}", id, movieDto);

        Movie movieUpdated = movieRentalService.updateMovie(
                id, movieConverter.convertDtoToModel(movieDto));
        MovieDto result = movieConverter.convertModelToDto(movieUpdated);

        log.trace("updateMovie: result={}", result);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);

    }


    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    MovieDto getMovie(@PathVariable Long id) {
        log.trace("getMovie: id={}", id);

        Movie movieReceived = this.movieRentalService.getMovie(id);
        MovieDto result = movieConverter.convertModelToDto(movieReceived);

        log.trace("getMovie: result={}", result);
        return result;
    }


    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<MovieDto> getAllMovies() {
        log.trace("getAllMovies --- method entered");

        Set<Movie> movies = this.movieRentalService.getAllMovies();
        Set<MovieDto> dataTransferObjects = movieConverter.convertModelsToDtos(movies);
        //MoviesDto result = new MoviesDto(dataTransferObjects);

        log.trace("getAllMovies: result={}", dataTransferObjects.toString());

        return new ArrayList<>(dataTransferObjects);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto) {
        log.trace("saveClient: clientDto={}", clientDto);

        Client clientSaved = this.movieRentalService.addClient(
                clientConverter.convertDtoToModel(clientDto));
        ClientDto result = clientConverter.convertModelToDto(clientSaved);

        log.trace("saveClient: result={}", result);

        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) {
        log.trace("deleteClient: id={}", id);

        this.movieRentalService.deleteClient(id);

        log.trace("deleteClient --- method finished");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id,
                           @RequestBody ClientDto clientDto) {
        log.trace("updateClient: id={}, clientDto={}", id, clientDto);

        Client clientUpdated = null;
        try {
            clientUpdated = this.movieRentalService.updateClient(
                    id, clientConverter.convertDtoToModel(clientDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientDto result = clientConverter.convertModelToDto(clientUpdated);

        log.trace("updateClient: result={}", result);

        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
    ClientDto getClient(@PathVariable Long id) {
        log.trace("getClient: id={}", id);

        Client clientReceived = this.movieRentalService.getClient(id);
        ClientDto result = clientConverter.convertModelToDto(clientReceived);

        log.trace("getClient: result={}", result);
        return result;
    }


    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<ClientDto> getAllClients() {
        log.trace("getAllClients --- method entered");

        Set<Client> clients = this.movieRentalService.getAllClients();
        Set<ClientDto> dataTransferObjects = clientConverter.convertModelsToDtos(clients);

        log.trace("getAllClients: result={}", dataTransferObjects.toString());

        return new ArrayList<>(dataTransferObjects);
    }

    @RequestMapping(value = "/movies/filterByTitle/{title}", method = RequestMethod.GET)
    public List<MovieDto> filterByTitle(@PathVariable String title) {
        log.trace("filterByTitle --- method entered");

        Set<Movie> movies = this.movieRentalService.filterByTitle(title);
        Set<MovieDto> dataTransferObjects = movieConverter.convertModelsToDtos(movies);

        log.trace("getAllMovies: result={}", dataTransferObjects.toString());

        return new ArrayList<>(dataTransferObjects);
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public List<RentalDto> getAllRentals() {
        log.trace("getAllRentals --- method entered");

        Set<Rental> rentals = this.movieRentalService.getAllRentals();
        Set<RentalDto> dataTransferObjects = rentalConverter.convertModelsToDtos(rentals);

        log.trace("getAllClients: result={}", dataTransferObjects.toString());

        return new ArrayList<>(dataTransferObjects);
    }

    @RequestMapping(value = "/rentals/{mid}/{cid}", method = RequestMethod.PUT)
    RentalDto updateRental(@PathVariable Long mid, @PathVariable Long cid,
                           @RequestBody RentalDto rentalDto) {
        log.trace("updateRental: mid={}, cid={}, rentalDto={}", mid, cid, rentalDto);

        Rental rentalUpdated = null;
        try {
            rentalUpdated = this.movieRentalService.updateRental(
                    RentalPK.builder()
                            .client(this.movieRentalService.getClient(cid))
                            .movie(this.movieRentalService.getMovie(mid))
                            //.client(cid)
                            //.movie(mid)
                            .build(),
                    rentalConverter.convertDtoToModel(rentalDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RentalDto result = rentalConverter.convertModelToDto(rentalUpdated);

        log.trace("updateRental: result={}", result);
        return result;
    }


    /*@RequestMapping(value = "/rentals/{mid}/{cid}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRental(@PathVariable Long mid, @PathVariable Long cid) {
        log.trace("deleteRental: mid={} cid={}", mid, cid);

        this.movieRentalService.deleteRental(
                RentalPK.builder()
                        .movie(this.movieRentalService.getMovie(mid))
                        //.movie(mid)
                        .client(this.movieRentalService.getClient(cid))
                        //.client(cid)
                        .build());

        log.trace("deleteRental --- method finished");

        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentalDto saveRental(@RequestBody RentalDto rentalDto) {
        log.trace("saveRental: rentalDto={}", rentalDto);

        Rental rentalSaved = this.movieRentalService.addRental(
                rentalConverter.convertDtoToModel(rentalDto));
        RentalDto result = rentalConverter.convertModelToDto(rentalSaved);

        log.trace("saveRental: result={}", result);

        return result;
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    UserDto getUser(@PathVariable String username) {
        log.trace("getUser: username={}", username);

        User userReceived = this.userService.getUserByUserName(username);
        UserDto result = userConverter.convertModelToDto(userReceived);

        log.trace("getUser: username={}", username);
        return result;
    }

}
