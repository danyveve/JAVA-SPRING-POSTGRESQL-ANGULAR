package ro.ubb.catalog.core.model;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithRentals",
                attributeNodes = @NamedAttributeNode(value = "rentals")),


        @NamedEntityGraph(name = "movieWithRentalsAndClient",
                attributeNodes = @NamedAttributeNode(value = "rentals",
                        subgraph = "rentalsWithClient"),
                subgraphs = @NamedSubgraph(name = "rentalsWithClient",
                        attributeNodes = @NamedAttributeNode(value =
                                "client")))
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Movie extends BaseEntity<Long>{

    private String title;
    private int year;
    private int duration;
    private String genre;
    private double imdbRating;
    private String trailerLink;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch =
            FetchType.LAZY, orphanRemoval = true)
    private Set<Rental> rentals = new HashSet<>();

    public Set<Client> getClients() {
        rentals = rentals == null ? new HashSet<>() :
                rentals;
        return Collections.unmodifiableSet(
                this.rentals.stream().
                        map(Rental::getClient).
                        collect(Collectors.toSet()));
    }

    public void addClient(Client client) {
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setMovie(this);
        rentals.add(rental);
    }

    public void addClients(Set<Client> clients) {
        clients.forEach(this::addClient);
    }

    public void addDates(Client client, LocalDate dateRented, LocalDate dateDue) {
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setDateRented(dateRented);
        rental.setDateDue(dateDue);
        rental.setMovie(this);
        rentals.add(rental);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie that = (Movie) o;

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
