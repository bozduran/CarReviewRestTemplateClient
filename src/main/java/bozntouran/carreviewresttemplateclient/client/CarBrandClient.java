package bozntouran.carreviewresttemplateclient.client;

import bozntouran.carreviewresttemplateclient.model.CarBrandDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CarBrandClient {
    Page<CarBrandDTO> getCarBrand();

    Page<CarBrandDTO> getCarBrand(String brandName);
    CarBrandDTO getCarBrandByID(UUID uuid);

    CarBrandDTO postCarBrand(CarBrandDTO newCarBrandDTO);
    CarBrandDTO updateCarBrand(CarBrandDTO carBrandDTO);
    void deleteCarBrand(UUID uuid);
}
