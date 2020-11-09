import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {MovieService} from "../shared/movie.service";

@Component({
  selector: 'app-movie-add',
  templateUrl: './movie-add.component.html',
  styleUrls: ['./movie-add.component.css']
})
export class MovieAddComponent implements OnInit {
  @Output() movieEvent = new EventEmitter<Movie>();

  constructor(private movieService: MovieService) { }

  ngOnInit() {
  }

  sendMovie(movie: Movie) {
    this.movieEvent.emit(movie);
  }

  add(title: string, year: number, duration: number, genre: string, imdbRating: number, trailerLink: string): void {
    title = title.trim();
    genre = genre.trim();
    trailerLink = trailerLink.trim();
    if (!title || !genre || !year || !duration || !imdbRating || !trailerLink) {
      return;
    }
    this.movieService.addMovie({title, year, duration, genre, imdbRating, trailerLink} as Movie)
      .subscribe(movie => {
        this.sendMovie(movie);
      });

  }

}
