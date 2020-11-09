import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Movie} from "../../movies/shared/movie.model";

@Component({
  selector: 'app-client-add',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  @Output() clientEvent = new EventEmitter<Client>();


  constructor(private clientService: ClientService) { }

  ngOnInit() {
  }

  sendClient(client: Client) {
    this.clientEvent.emit(client);
  }

  add(name: string, email: string, dateOfBirth: string, dateOfRegister: string): void {
    name = name.trim();
    email = email.trim();
    dateOfBirth = dateOfBirth.trim();
    dateOfRegister = dateOfRegister.trim();

    if (!name || !email || !dateOfBirth || !dateOfRegister) {
      return;
    }
    this.clientService.addClient({name, email, dateOfBirth, dateOfRegister} as Client)
      .subscribe(client => {
        this.sendClient(client);
      });

  }

}
