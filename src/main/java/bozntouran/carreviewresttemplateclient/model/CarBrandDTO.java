package bozntouran.carreviewresttemplateclient.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CarBrandDTO {
    private UUID id;
    private  Integer version;

    private  String brandName;

    private  String countryOfOrigin;

    private  Integer yearOfFoundation;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
