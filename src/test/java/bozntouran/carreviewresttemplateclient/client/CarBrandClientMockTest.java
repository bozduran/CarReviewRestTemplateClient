package bozntouran.carreviewresttemplateclient.client;

import bozntouran.carreviewresttemplateclient.config.OAuthClientInterceptor;
import bozntouran.carreviewresttemplateclient.config.RestTemplateBuilderConfig;
import bozntouran.carreviewresttemplateclient.model.CarBrandDTO;
import bozntouran.carreviewresttemplateclient.pageimpl.CarBrandDTOPageImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
@Import(RestTemplateBuilderConfig.class)
public class CarBrandClientMockTest {

    static final String URL = "http://localhost:8080";
    public static final String BEARER_TEST = "Bearer test";

    @Autowired
    CarBrandClientImpl carBrandClient;

    MockRestServiceServer server;

    @Autowired
    RestTemplateBuilder restTemplateBuilderConfigured;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    RestTemplateBuilder mockRestTemplateBuilder = new RestTemplateBuilder(new MockServerRestTemplateCustomizer());

    CarBrandDTO carBrandDTO;
    String dtoJson;

    @MockBean
    OAuth2AuthorizedClientManager manager;

    @TestConfiguration
    public static class TestConfig {

        @Bean
        ClientRegistrationRepository clientRegistrationRepository() {
            return new InMemoryClientRegistrationRepository(ClientRegistration
                    .withRegistrationId("springauth")
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .clientId("test")
                    .tokenUri("test")
                    .build());
        }

        @Bean
        OAuth2AuthorizedClientService auth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository){
            return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
        }

        @Bean
        OAuthClientInterceptor oAuthClientInterceptor(OAuth2AuthorizedClientManager manager, ClientRegistrationRepository clientRegistrationRepository){
            return new OAuthClientInterceptor(manager, clientRegistrationRepository);
        }
    }

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        ClientRegistration clientRegistration = clientRegistrationRepository
                .findByRegistrationId("springauth");

        OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                "test", Instant.MIN, Instant.MAX);

        when(manager.authorize(any())).thenReturn(new OAuth2AuthorizedClient(clientRegistration,
                "test", token));

        RestTemplate restTemplate = restTemplateBuilderConfigured.build();
        server = MockRestServiceServer.bindTo(restTemplate).build();
        when(mockRestTemplateBuilder.build()).thenReturn(restTemplate);
        carBrandClient = new CarBrandClientImpl(mockRestTemplateBuilder);
    }
    @Test
    void getCarBrand() throws JsonProcessingException {

        String response = objectMapper.writeValueAsString(getPage());

        URI uri = UriComponentsBuilder.fromHttpUrl(URL +"/api/car-brand" )
                .build().toUri();

        server.expect(method(HttpMethod.GET))
                .andExpect(requestTo(uri))
                .andExpect(header("Authorization", BEARER_TEST))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        Page<CarBrandDTO> responsePage = carBrandClient
                .getCarBrand();

        assertThat(responsePage.getContent().size()).isEqualTo(1);

    }

    CarBrandDTO getCarBrandDTO(){
        return CarBrandDTO.builder().brandName("asdasd").countryOfOrigin("asdasdasd").yearOfFoundation(2000)
                .build();
    }

    CarBrandDTOPageImpl getPage(){
        return new CarBrandDTOPageImpl(Arrays.asList(getCarBrandDTO()), 1, 25, 1);
    }
}
