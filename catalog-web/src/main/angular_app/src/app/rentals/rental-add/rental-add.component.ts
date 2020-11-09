import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {Movie} from "../../movies/shared/movie.model";
import {Client} from "../../clients/shared/client.model";
import {RentalService} from "../shared/rental.service";
import {MovieService} from "../../movies/shared/movie.service";
import {ClientService} from "../../clients/shared/client.service";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";

@Component({
  selector: 'app-rental-add',
  templateUrl: './rental-add.component.html',
  styleUrls: ['./rental-add.component.css']
})
export class RentalAddComponent implements OnInit {
  movies: Movie[];
  clients: Client[];
  selectedMovie: Movie;
  selectedClient: Client;
  dateRented: string = null;
  dateDue: string = null;

  constructor(private rentalService: RentalService,
              private movieService: MovieService,
              private clientService: ClientService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit() {
    this.getClients();
    this.getMovies();
  }

  getClients(): void {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients);
  }

  getMovies(): void {
    this.movieService.getMovies()
      .subscribe(
        movies => this.movies = movies);
  }

  onSelectMovie(movie: Movie): void {
    this.selectedMovie = movie;
  }

  onSelectClient(client: Client): void {
    this.selectedClient = client;
  }

  goBack(): void {
    this.location.back();
  }

  add(movieID:number , clientID:number, movieTitle: string, clientName: string, dateRented: string, dateDue:string): void {
    movieTitle = movieTitle.trim();
    clientName = clientName.trim();
    dateRented = dateRented.trim();
    dateDue = dateDue.trim();
    if (!movieID || !clientID || !movieTitle || !clientName || !dateRented || !dateDue) {
      return;
    }
    this.rentalService.addRental({movieID, clientID, movieTitle, clientName, dateRented, dateDue} as Rental)
      .subscribe(result => this.goBack());
  }

}
