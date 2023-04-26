package bozntouran.carreviewresttemplateclient;

import bozntouran.carreviewresttemplateclient.client.CarBrandClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarReviewRestTemplateClientApplication {

    @Autowired
    CarBrandClient carBrandClient;
    public static void main(String[] args) {
        SpringApplication.run(CarReviewRestTemplateClientApplication.class, args);
    }

}
