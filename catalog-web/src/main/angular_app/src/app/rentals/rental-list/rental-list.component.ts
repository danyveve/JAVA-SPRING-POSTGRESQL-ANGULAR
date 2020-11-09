import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css']
})
export class RentalListComponent implements OnInit {
  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;


  constructor(private rentalService: RentalService,
              private router: Router) {
  }

  ngOnInit() {
    this.getRentals();
  }

  getRentals(): void {
    this.rentalService.getRentals()
      .subscribe(
        rentals => this.rentals = rentals,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }

  gotoDetail(): void {
    this.router.navigate(['/rental/detail', this.selectedRental.movieID, this.selectedRental.clientID]);
  }

  delete(rental: Rental): void {
    this.rentals = this.rentals.filter(r => r !== rental);
    this.rentalService.deleteRental(rental, rental).subscribe();
  }



  receiveRental($event) {
    this.rentals = [$event, ...this.rentals];
  }

}
