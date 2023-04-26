package bozntouran.carreviewresttemplateclient.client;

import bozntouran.carreviewresttemplateclient.model.CarBrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarBrandClientImpl implements CarBrandClient{

    private final RestTemplateBuilder restTemplateBuilder;

    private final String GET_CAR_BRAND_URL = "/api/car-brand";
    private final String GET_CAR_BRAND_BY_ID = GET_CAR_BRAND_URL + "/{carBrandId}";
    @Override
    public Page<CarBrandDTO> getCarBrand() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<CarBrandClientImpl> pageResponse =
                restTemplate.getForEntity(GET_CAR_BRAND_URL, CarBrandClientImpl.class);

        return (Page<CarBrandDTO>) pageResponse.getBody();
    }
    @Override
    public Page<CarBrandDTO> getCarBrand(String brandName) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_CAR_BRAND_URL);
        if (brandName!=null){
            uriComponentsBuilder.queryParam("brandName",brandName);
        }

        RestTemplate restTemplate = restTemplateBuilder.build();
/*        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(GET_CAR_BRAND_URL,String.class);

        ResponseEntity<Map> mapResponseEntity =
                restTemplate.getForEntity(GET_CAR_BRAND_URL,Map.class);

        ResponseEntity<JsonNode> jsonNodeResponseEntity =
                restTemplate.getForEntity(GET_CAR_BRAND_URL,JsonNode.class);

        jsonNodeResponseEntity.getBody().forEach(jsonNode -> {
            System.out.println(jsonNode.get("brandName"));
        });*/

        ResponseEntity<CarBrandClientImpl> pageResponse =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), CarBrandClientImpl.class);

        return (Page<CarBrandDTO>) pageResponse.getBody();
    }

    @Override
    public CarBrandDTO getCarBrandByID(UUID uuid) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(GET_CAR_BRAND_BY_ID,CarBrandDTO.class,uuid);
    }

    @Override
    public CarBrandDTO postCarBrand(CarBrandDTO newCarBrandDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        URI uri = restTemplate.postForLocation(GET_CAR_BRAND_BY_ID,newCarBrandDTO);
        return restTemplate.getForObject(uri.getPath(),CarBrandDTO.class);
    }

    @Override
    public CarBrandDTO updateCarBrand(CarBrandDTO carBrandDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_CAR_BRAND_BY_ID,carBrandDTO,carBrandDTO.getId());
        return getCarBrandByID(carBrandDTO.getId());
    }

    @Override
    public void deleteCarBrand(UUID uuid) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_CAR_BRAND_BY_ID,uuid);
    }
}
