import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {BehaviorSubject, Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {catchError} from "rxjs/operators";
import {ErrorObservable} from 'rxjs/observable/ErrorObservable';
import {of} from "rxjs/observable/of";
import {Client} from "../../clients/shared/client.model";
import {Rental} from "./rental.model";

@Injectable()
export class RentalService {
  private rentalsUrl = 'http://localhost:8080/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rental[]> {
    console.log(this.httpClient.get<Array<Rental>>(this.rentalsUrl));
    return this.httpClient
      .get<Array<Rental>>(this.rentalsUrl,{withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  getRental(mid: number, cid: number): Observable<Rental> {
    return this.getRentals()
      .map(rentals => rentals.find(rental => rental.movieID === mid && rental.clientID === cid)).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  update(rental): Observable<Rental> {
    const url = `${this.rentalsUrl}/${rental.movieID}/${rental.clientID}`;
    return this.httpClient
      .put<Rental>(url, rental, {withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  deleteRental(rentalmid: Rental | number, rentalcid: Rental | number): Observable<Rental> {
    const mid = typeof rentalmid === 'number' ? rentalmid : rentalmid.movieID;
    const cid = typeof rentalcid ==='number' ? rentalcid : rentalcid.clientID;
    const url = `${this.rentalsUrl}/${mid}/${cid}`;

    return this.httpClient.delete<Rental>(url, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }

  /** POST: add a new hero to the server */
  addRental(rental: Rental): Observable<Rental> {
    return this.httpClient.post<Rental>(this.rentalsUrl, rental, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }


}
