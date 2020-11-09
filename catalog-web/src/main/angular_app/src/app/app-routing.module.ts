import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MoviesComponent} from "./movies/movies.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {ClientsComponent} from "./clients/clients.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalDetailComponent} from "./rentals/rental-detail/rental-detail.component";
import {RentalAddComponent} from "./rentals/rental-add/rental-add.component";
import {LoginComponent} from "./login/login.component";
import {ClientGuard} from "./login/client-guard";
import {AdminGuard} from "./login/admin-guard";

const routes: Routes = [
  // { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'movies', component: MoviesComponent, canActivate: [ClientGuard]},
  {path: 'movie/detail/:id', component: MovieDetailComponent, canActivate: [ClientGuard]},
  {path: 'clients', component: ClientsComponent, canActivate: [ClientGuard]},
  {path: 'client/detail/:id', component: ClientDetailComponent, canActivate: [ClientGuard]},
  {path: 'rentals', component: RentalsComponent, canActivate: [AdminGuard]},
  {path: 'rental/detail/:mid/:cid' , component: RentalDetailComponent, canActivate: [AdminGuard]},
  {path: 'addRental', component: RentalAddComponent, canActivate: [AdminGuard]},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
