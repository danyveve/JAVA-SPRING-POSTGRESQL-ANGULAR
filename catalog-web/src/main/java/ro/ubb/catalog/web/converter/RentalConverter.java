package ro.ubb.catalog.web.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.service.MovieRentalService;
import ro.ubb.catalog.web.dto.RentalDto;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RentalConverter extends AbstractConverter<Rental, RentalDto> {
    @Autowired
    MovieRentalService movieRentalService;

    @Override
    public Rental convertDtoToModel(RentalDto rentalDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Rental rental = Rental.builder()
                .movie(movieRentalService.getMovie(rentalDto.getMovieID()))
                .client(movieRentalService.getClient(rentalDto.getClientID()))
                .dateRented(LocalDate.parse(rentalDto.getDateRented(), formatter))
                .dateDue(LocalDate.parse(rentalDto.getDateDue(), formatter))
                .build();

        return rental;
    }

    @Override
    public RentalDto convertModelToDto(Rental rental) {
        return RentalDto.builder()
                .movieID(rental.getMovie().getId())
                .clientID(rental.getClient().getId())
                .movieTitle(rental.getMovie().getTitle())
                .clientName(rental.getClient().getName())
                .dateRented(rental.getDateRented().toString())
                .dateDue(rental.getDateDue().toString())
                .build();
    }
}
