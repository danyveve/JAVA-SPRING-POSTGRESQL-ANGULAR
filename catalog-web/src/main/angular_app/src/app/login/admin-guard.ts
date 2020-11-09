import {CanActivate} from "@angular/router";

export class AdminGuard implements CanActivate {
  canActivate() {
    return sessionStorage.getItem("userRole") === "ADMIN";
  }
}
