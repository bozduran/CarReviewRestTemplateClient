package bozntouran.carreviewresttemplateclient.client;

import bozntouran.carreviewresttemplateclient.model.CarModelDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CarModelClientImpl implements CarModelClient{
    @Override
    public Page<CarModelDTO> getCarModel() {
        return null;
    }
}
