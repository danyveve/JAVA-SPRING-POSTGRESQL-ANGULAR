import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Movie Rental';

  constructor(private httpClient: HttpClient, private router: Router) {

  }

  logout() {
    this.httpClient.post(`http://localhost:8080/logout`, null, {withCredentials: true}).subscribe((result) => {
      this.router.navigateByUrl("/");
      sessionStorage.removeItem("userRole");
    });
  }
}
