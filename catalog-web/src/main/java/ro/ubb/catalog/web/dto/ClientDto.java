package ro.ubb.catalog.web.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class ClientDto extends BaseDto {
    private String name;
    private String email;
    private String dateOfBirth;
    private String dateOfRegister;
}
