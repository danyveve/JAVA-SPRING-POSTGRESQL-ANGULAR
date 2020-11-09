import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Movie} from "../shared/movie.model";
import {Router} from "@angular/router";
import {Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, switchMap} from "rxjs/operators";

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;

  constructor(private movieService: MovieService,
              private router: Router) {
  }


  ngOnInit() {
    this.getMovies();
  }

  getMovies(): void {
    this.movieService.getMovies()
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }


  delete(movie: Movie): void {
    this.movies = this.movies.filter(m => m !== movie);
    this.movieService.deleteMovie(movie).subscribe();
  }

  filterByTitle(term: string): void {
    term = term.trim();
    if (!term) {
      this.getMovies();
    }
    else {
      this.movieService.filterByTitle(term)
        .subscribe(
          movies => {
            this.movies = movies;
          },
          error => this.errorMessage = <any>error
        );
    }
  }

  receiveMovie($event) {
    this.movies = [$event, ...this.movies];
  }
}


