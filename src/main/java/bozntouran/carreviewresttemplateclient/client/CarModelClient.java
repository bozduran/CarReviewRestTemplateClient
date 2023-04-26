package bozntouran.carreviewresttemplateclient.client;

import bozntouran.carreviewresttemplateclient.model.CarModelDTO;
import org.springframework.data.domain.Page;

public interface CarModelClient {

    Page<CarModelDTO> getCarModel();
}
