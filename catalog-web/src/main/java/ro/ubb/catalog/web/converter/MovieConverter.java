package ro.ubb.catalog.web.converter;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.web.dto.MovieDto;

@Component
public class MovieConverter extends AbstractConverterBaseEntity<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto movieDto) {
        Movie movie = Movie.builder()
                .title(movieDto.getTitle())
                .year(movieDto.getYear())
                .duration(movieDto.getDuration())
                .genre(movieDto.getGenre())
                .imdbRating(movieDto.getImdbRating())
                .trailerLink(movieDto.getTrailerLink())
                .build();
        movie.setId(movieDto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto movieDto = MovieDto.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .imdbRating(movie.getImdbRating())
                .trailerLink(movie.getTrailerLink())
                .build();
        movieDto.setId(movie.getId());
        return movieDto;
    }

}
