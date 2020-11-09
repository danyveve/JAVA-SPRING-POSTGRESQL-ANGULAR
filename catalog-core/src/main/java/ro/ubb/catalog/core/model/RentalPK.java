package ro.ubb.catalog.core.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class RentalPK implements Serializable {
    private Movie movie;

    private Client client;
}
