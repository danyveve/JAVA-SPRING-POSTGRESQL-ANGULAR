package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
public class RentalDto extends BaseDto {
    private Long movieID;
    private Long clientID;
    private String movieTitle;
    private String clientName;
    private String dateRented;
    private String dateDue;
}
