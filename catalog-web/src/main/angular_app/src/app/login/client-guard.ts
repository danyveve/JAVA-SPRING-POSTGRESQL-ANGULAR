import {CanActivate} from "@angular/router";

export class ClientGuard implements CanActivate {
  constructor() {
  }
  canActivate() {
    return sessionStorage.getItem("userRole") === "CLIENT" || sessionStorage.getItem("userRole") === "ADMIN";
  }
}
