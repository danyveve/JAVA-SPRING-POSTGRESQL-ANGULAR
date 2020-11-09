import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Client} from "../shared/client.model";
import {Router} from "@angular/router";
import {Movie} from "../../movies/shared/movie.model";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;


  constructor(private clientService: ClientService,
              private router: Router) {
  }

  ngOnInit() {
    this.getClients();
  }

  getClients(): void {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
  }

  gotoDetail(): void {
    this.router.navigate(['/client/detail', this.selectedClient.id]);
  }

  delete(client: Client): void {
    this.clients = this.clients.filter(c => c !== client);
    this.clientService.deleteClient(client).subscribe();
  }



  receiveClient($event) {
    this.clients = [$event, ...this.clients];
  }

}
