package bozntouran.carreviewresttemplateclient.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CarBrandClientImplTest {

    @Autowired
    private CarBrandClientImpl carBrandClient;

    @Test
    void getCarBrand() {
        carBrandClient.getCarBrand();
    }
}