package ro.ubb.catalog.core.repository;

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
import ro.ubb.catalog.core.service.MovieRentalService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = "/db-data.xml")
public class MovieRepoTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void findAll() throws Exception {
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be four students", 4, movies.size());
    }

    @Test
    public void addMovie() throws Exception {
        Movie m = Movie.builder().title("t5").year(2001).genre("g5").imdbRating(2.2).trailerLink("http").duration(100).build();
        Movie movie = movieRepository.save(m);
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be four students", 5, movies.size());
        assertEquals("title should be t5, duration should be 100", "t5", movie.getTitle());
        assertEquals("title should be t5, duration should be 100", 100, movie.getDuration());

    }


    @Test(expected = NoSuchElementException.class)
    public void getMovie() throws Exception {
        Movie mo = movieRepository.findById(-1L).get();
        assertEquals("title should be t5, duration should be 100", "t1", mo.getTitle());
        assertEquals("title should be t5, duration should be 100", 111, mo.getDuration());
        movieRepository.findById(7L).get();
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteMovie() throws Exception {
        List<Movie> movies = movieRepository.findAll();
        assertEquals("there should be four students", 4, movies.size());
        assertEquals("title should be t1", "t1", movieRepository.findById(-1L).get().getTitle());
        movieRepository.deleteById(-1L);
        movies = movieRepository.findAll();
        assertEquals("there should be four students", 3, movies.size());
        movieRepository.findById(-1L).get();
    }

}