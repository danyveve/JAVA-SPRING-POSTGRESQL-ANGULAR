package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
@IdClass(RentalPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Rental implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "dateRented")
    private LocalDate dateRented;

    @Column(name = "dateDue")
    private LocalDate dateDue;

    @Override
    public String toString() {
        return "Rental{" +
                "movie=" + movie.getId() +
                ", client=" + client.getId() +
                ", dateRented=" + dateRented +
                ", dateDue=" + dateDue +
                '}';
    }
}
