package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "clientWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),


        @NamedEntityGraph(name = "clientWithRentalsAndMovie",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalsWithMovie"),
                subgraphs = @NamedSubgraph(name = "rentalsWithMovie",
                        attributeNodes = @NamedAttributeNode(value =
                                "movie")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Client extends BaseEntity<Long> {

    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRegister;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY)
    private Set<Rental> rentals = new HashSet<>();

    public Set<Movie> getMovies() {
        rentals = rentals == null ? new HashSet<>() :
                rentals;
        return Collections.unmodifiableSet(
                this.rentals.stream().
                        map(Rental::getMovie).
                        collect(Collectors.toSet()));
    }

    public void addMovie(Movie movie) {
        Rental rental = new Rental();
        rental.setMovie(movie);
        rental.setClient(this);
        rentals.add(rental);
    }

    public void addMovies(Set<Movie> movies) {
        movies.forEach(this::addMovie);
    }

    public void addDates(Movie movie, LocalDate dateRented, LocalDate dateDue) {
        Rental rental = new Rental();
        rental.setMovie(movie);
        rental.setDateRented(dateRented);
        rental.setDateDue(dateDue);
        rental.setClient(this);
        rentals.add(rental);
    }

    public void updateRental(Movie movie, LocalDate dateRented, LocalDate dateDue){
        Rental result = this.rentals.stream()
                .filter(r -> r.getClient().getId().equals(this.getId())
                        && r.getMovie().getId().equals(movie.getId()))
                .findFirst().get();
        result.setDateRented(dateRented);
        result.setDateDue(dateDue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client that = (Client) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
