import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import { MovieListComponent } from './movies/movie-list/movie-list.component';
import { MovieDetailComponent } from './movies/movie-detail/movie-detail.component';
import { MoviesComponent } from './movies/movies.component';
import {MovieService} from "./movies/shared/movie.service";
import { ClientsComponent } from './clients/clients.component';
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import { RentalsComponent } from './rentals/rentals.component';
import { MovieAddComponent } from './movies/movie-add/movie-add.component';
import { ClientAddComponent } from './clients/client-add/client-add.component';
import { RentalListComponent } from './rentals/rental-list/rental-list.component';
import {RentalService} from "./rentals/shared/rental.service";
import { RentalDetailComponent } from './rentals/rental-detail/rental-detail.component';
import { RentalAddComponent } from './rentals/rental-add/rental-add.component';
import { FirstMenuComponent } from './first-menu/first-menu.component';
import { LoginComponent } from './login/login.component';
import {AdminGuard} from "./login/admin-guard";
import {ClientGuard} from "./login/client-guard";
import { XyzComponent } from './rentals/xyz/xyz.component';

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieDetailComponent,
    MoviesComponent,
    ClientsComponent,
    ClientDetailComponent,
    ClientListComponent,
    MovieAddComponent,
    ClientAddComponent,
    RentalListComponent,
    RentalsComponent,
    RentalDetailComponent,
    RentalAddComponent,
    FirstMenuComponent,
    LoginComponent,
    XyzComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [MovieService, ClientService, RentalService, AdminGuard, ClientGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
