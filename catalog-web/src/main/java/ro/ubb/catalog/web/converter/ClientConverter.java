package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.web.dto.ClientDto;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ClientConverter extends AbstractConverterBaseEntity<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto clientDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Client client = Client.builder()
                .name(clientDto.getName())
                .email(clientDto.getEmail())
                .dateOfBirth(LocalDate.parse(clientDto.getDateOfBirth(), formatter))
                .dateOfRegister(LocalDate.parse(clientDto.getDateOfRegister(), formatter))
                .build();
        client.setId(clientDto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        ClientDto clientDto = ClientDto.builder()
                .name(client.getName())
                .email(client.getEmail())
                .dateOfBirth(client.getDateOfBirth().toString())
                .dateOfRegister(client.getDateOfRegister().toString())
                .build();
        clientDto.setId(client.getId());
        return clientDto;
    }
}
