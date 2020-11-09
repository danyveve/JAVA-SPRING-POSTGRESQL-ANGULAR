package ro.ubb.catalog.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/db-data.xml")
public class MovieServiceTest {

    @Autowired
    private MovieRentalService movieRentalService;

    @Test
    public void findAll() throws Exception {
        Set<Movie> movies = movieRentalService.getAllMovies();
        assertEquals("there should be four students", 4, movies.size());
    }

    @Test
    public void addMovie() throws Exception {
        Movie m = Movie.builder().title("t5").year(2001).genre("g5").imdbRating(2.2).trailerLink("http").duration(100).build();
        Movie movie = movieRentalService.addMovie(m);
        Set<Movie> movies = movieRentalService.getAllMovies();
        assertEquals("there should be four students", 5, movies.size());
        assertEquals("title should be t5, duration should be 100", "t5", movie.getTitle());
        assertEquals("title should be t5, duration should be 100", 100, movie.getDuration());

    }

    @Test
    public void updateMovie() throws Exception {
        Movie m = Movie.builder().title("t5").year(2001).genre("g5").imdbRating(2.2).trailerLink("http").duration(100).build();
        movieRentalService.updateMovie(-1L, m);
        Movie mo = movieRentalService.getMovie(-1L);
        assertEquals("title should be t5, duration should be 100", "t5", mo.getTitle());
        assertEquals("title should be t5, duration should be 100", 100, mo.getDuration());
        Set<Movie> movies = movieRentalService.getAllMovies();
        assertEquals("there should be four students", 4, movies.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void getMovie() throws Exception {
        Movie mo = movieRentalService.getMovie(-1L);
        assertEquals("title should be t5, duration should be 100", "t1", mo.getTitle());
        assertEquals("title should be t5, duration should be 100", 111, mo.getDuration());
        movieRentalService.getMovie(7L);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteMovie() throws Exception {
        Set<Movie> movies = movieRentalService.getAllMovies();
        assertEquals("there should be four students", 4, movies.size());
        assertEquals("title should be t1", "t1", movieRentalService.getMovie(-1L).getTitle());
        movieRentalService.deleteMovie(-1L);
        movies = movieRentalService.getAllMovies();
        assertEquals("there should be four students", 3, movies.size());
        movieRentalService.getMovie(-1L);
    }

}