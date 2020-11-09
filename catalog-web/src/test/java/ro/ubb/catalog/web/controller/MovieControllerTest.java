package ro.ubb.catalog.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.service.MovieRentalService;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.dto.MovieDto;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MovieControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RentalsController rentalsController;

    @Mock
    private MovieRentalService movieRentalService;

    @Mock
    private MovieConverter movieConverter;

    private Movie movie1;
    private Movie movie2;
    private MovieDto movieDto1;
    private MovieDto movieDto2;


    @Before
    public void setup() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(rentalsController)
                .build();
        initData();
    }

    @Test
    public void getMovies() throws Exception {
        Set<Movie> movies = Set.of(movie1, movie2);
        Set<MovieDto> movieDtos =
                new HashSet<>(Arrays.asList(movieDto1, movieDto2));
        when(movieRentalService.getAllMovies()).thenReturn(movies);
        when(movieConverter.convertModelsToDtos(movies)).thenReturn(movieDtos);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", anyOf(is("t1"), is("t2"))))
                .andExpect(jsonPath("$[1].genre", anyOf(is("g1"), is("g2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println(result);

        verify(movieRentalService, times(1)).getAllMovies();
        verify(movieConverter, times(1)).convertModelsToDtos(movies);
        verifyNoMoreInteractions(movieRentalService, movieConverter);


    }

    @Test
    public void updateMovie() throws Exception {
        when(movieConverter.convertDtoToModel(any(MovieDto.class))).thenReturn(movie1);
        when(movieConverter.convertModelToDto(any(Movie.class))).thenReturn(movieDto1);
        when(movieRentalService.updateMovie(movie1.getId(), movie1))
                .thenReturn(movie1);

//        Map<String, StudentDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        //.put("/api/movies/{id}", movie1.getId(), movieDto1)
                        .put("/api/movies/{id}", movie1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(movieDto1)))
                .andExpect(status().isOk())
                .andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("t1")));

        verify(movieRentalService, times(1)).updateMovie(movie1.getId(), movie1);
        verify(movieConverter, times(1)).convertModelToDto(movie1);
        verify(movieConverter, times(1)).convertDtoToModel(any(MovieDto.class));
        verifyNoMoreInteractions(movieRentalService, movieConverter);
    }

    private String toJsonString(Map<String, MovieDto> movieDtoMap) {
        try {
            return new ObjectMapper().writeValueAsString(movieDtoMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString(MovieDto movieDto) {
        try {
            return new ObjectMapper().writeValueAsString(movieDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createMovie() throws Exception {
        when(movieConverter.convertDtoToModel(movieDto1)).thenReturn(movie1);
        when(movieConverter.convertModelToDto(movie1)).thenReturn(movieDto1);
        when(movieRentalService.addMovie(movie1))
                .thenReturn(movie1);

//        Map<String, StudentDto> studentDtoMap = new HashMap<>();
//        studentDtoMap.put("student", studentDto1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        //.post("/api/movies",movieDto1)
                        .post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(movieDto1)))
                .andExpect(status().isOk())
                .andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("t1")));

        verify(movieRentalService, times(1)).addMovie(movie1);
        verify(movieConverter, times(1)).convertModelToDto(movie1);
        verify(movieConverter, times(1)).convertDtoToModel(movieDto1);
        verifyNoMoreInteractions(movieRentalService, movieConverter);
    }

    @Test
    public void deleteMovie() throws Exception {
        Mockito.doNothing().when(movieRentalService).deleteMovie(movie1.getId());


        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/movies/{id}", movie1.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(movieDto1)))
                .andExpect(status().isOk())
                .andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8));

        verify(movieRentalService, times(1)).deleteMovie(movie1.getId());
        verifyNoMoreInteractions(movieRentalService);
    }

    private void initData() {
        movie1 = Movie.builder().title("t1").duration(2001).trailerLink("h1").imdbRating(1.1).genre("g1").year(2001).build();
        movie1.setId(1L);
        movie2 = Movie.builder().title("t2").duration(2002).trailerLink("h2").imdbRating(2.2).genre("g2").year(2002).build();
        movie2.setId(2L);

        movieDto1 = createMovieDto(movie1);
        movieDto2 = createMovieDto(movie2);

    }

    private MovieDto createMovieDto(Movie movie) {
        MovieDto movieDto = MovieDto.builder()
                .title(movie.getTitle())
                .duration(movie.getDuration())
                .year(movie.getYear())
                .genre(movie.getGenre())
                .imdbRating(movie.getImdbRating())
                .trailerLink(movie.getTrailerLink())
                .build();
        movieDto.setId(movie.getId());
        return movieDto;
    }


}