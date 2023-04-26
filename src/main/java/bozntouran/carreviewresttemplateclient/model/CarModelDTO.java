package bozntouran.carreviewresttemplateclient.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
public class CarModelDTO {
    private UUID id;
    private  Integer version;

    private  String modelName;
    private  Integer yearOfProduction;

    private String drive;
    private String fuelType;
    private String transmission;
    private String carType;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
