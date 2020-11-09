package ro.ubb.catalog.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDto extends BaseDto {

    private String title;
    private int year;
    private int duration;
    private String genre;
    private double imdbRating;
    private String trailerLink;

    @Override
    public String toString() {
        return "MovieDto{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", duration=" + duration +
                ", genre=" + genre +
                ", imdbRating=" + imdbRating +
                ", trailerLink=" + trailerLink +
                "} " + super.toString();
    }
}
