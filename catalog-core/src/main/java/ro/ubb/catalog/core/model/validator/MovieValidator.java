package ro.ubb.catalog.core.model.validator;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Movie;

import java.util.Calendar;
import java.util.stream.Stream;

@Component
public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        //if(entity.getTitle().equals("")) throw new ValidatorException("Movie without title!");
        Stream.of(entity.getTitle()).filter(title -> !title.equals("")).findFirst()
                .orElseThrow(() -> new ValidatorException("Movie without title!"));
        //if(entity.getYear() < 1888 || entity.getYear() > Calendar.getInstance().get(Calendar.YEAR))
          //  throw new ValidatorException("Movie year cannot be true!");
        Stream.of(entity.getYear()).filter(year -> !(year<1888 || year>Calendar.getInstance().get(Calendar.YEAR))).findFirst()
                .orElseThrow(() -> new ValidatorException("Movie year cannot be true!"));
        if(entity.getDuration() < 0) throw new ValidatorException("Duration must be a positive integer!");
        if(entity.getGenre().equals("")) throw new ValidatorException("Movie without genre!");
        if(entity.getImdbRating() < 0 || entity.getImdbRating() > 10) throw new ValidatorException("IMDB Rating must be between 0 and 10!");
        if(entity.getTrailerLink().equals("")) throw new ValidatorException("Movie without trailer link");
    }
}
