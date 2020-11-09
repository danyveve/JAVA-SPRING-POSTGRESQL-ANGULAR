import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {User} from "./user.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router, private httpClient: HttpClient) {
  }

  ngOnInit() {
  }

  login(username: string, password: string) {
    this.httpClient.post(`http://localhost:8080/login?username=${username}&password=${password}`, null, {withCredentials: true}).subscribe((result) => {
      this.httpClient.get<User>(`http://localhost:8080/api/users/${username}`, {withCredentials: true}).subscribe(user => {
        sessionStorage.setItem("userRole", user.role);
        this.router.navigateByUrl("/movies");
      })
    });
  }
}
