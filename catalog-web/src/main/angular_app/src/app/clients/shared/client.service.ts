import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Client} from "./client.model";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {Movie} from "../../movies/shared/movie.model";
import {catchError} from "rxjs/operators";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";

@Injectable()
export class ClientService {
  private clientsUrl = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Client[]> {
    console.log("get here");
    console.log(this.httpClient.get<Array<Client>>(this.clientsUrl));
    return this.httpClient
      .get<Array<Client>>(this.clientsUrl, {withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  getClient(id: number): Observable<Client> {
    return this.getClients()
      .map(clients => clients.find(client => client.id === id)).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  update(client): Observable<Client> {
    const url = `${this.clientsUrl}/${client.id}`;
    return this.httpClient
      .put<Client>(url, client, {withCredentials: true}).pipe(catchError(err => {
        alert(err.message);
        console.error(err.message);
        console.log("Error is handled");
        //return throwError(new Error"Error thrown from catchError");
        return ErrorObservable.create(new Error("Error"));
      }));
  }

  deleteClient(client: Client | number): Observable<Client> {
    const id = typeof client === 'number' ? client : client.id;
    const url = `${this.clientsUrl}/${id}`;

    return this.httpClient.delete<Client>(url, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }

  /** POST: add a new hero to the server */
  addClient(client: Client): Observable<Client> {
    return this.httpClient.post<Client>(this.clientsUrl, client, {withCredentials: true}).pipe(catchError(err => {
      alert(err.message);
      console.error(err.message);
      console.log("Error is handled");
      //return throwError(new Error"Error thrown from catchError");
      return ErrorObservable.create(new Error("Error"));
    }));
  }

}
