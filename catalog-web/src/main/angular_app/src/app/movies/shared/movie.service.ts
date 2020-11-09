import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Movie} from "./movie.model";

import {BehaviorSubject, Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {catchError} from "rxjs/operators";
import {ErrorObservable} from 'rxjs/observable/ErrorObservable';
import {of} from "rxjs/observable/of";

@Injectable()
export class MovieService {
  private moviesUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    console.log(this.httpClient.get<Array<Movie>>(this.moviesUrl));
    return this.httpClient
      .get<Array<Movie>>(this.moviesUrl, {withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  getMovie(id: number): Observable<Movie> {
    return this.getMovies()
      .map(movies => movies.find(movie => movie.id === id)).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  update(movie): Observable<Movie> {
    const url = `${this.moviesUrl}/${movie.id}`;
    return this.httpClient
      .put<Movie>(url, movie, {withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }


  deleteMovie(movie: Movie | number): Observable<Movie> {
    const id = typeof movie === 'number' ? movie : movie.id;
    const url = `${this.moviesUrl}/${id}`;

    return this.httpClient.delete<Movie>(url, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }


  /** POST: add a new hero to the server */
  addMovie(movie: Movie): Observable<Movie> {
    return this.httpClient.post<Movie>(this.moviesUrl, movie, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }

  /* GET heroes whose name contains search term */
  filterByTitle(term: string): Observable<Movie[]> {
    console.log("filteruiesc");
    if (!term.trim()) {
      return this.getMovies();
    }
    return this.httpClient.get<Movie[]>(`${this.moviesUrl}/filterByTitle/${term}`, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }

}
