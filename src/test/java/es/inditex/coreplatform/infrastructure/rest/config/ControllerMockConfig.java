package es.inditex.coreplatform.infrastructure.rest.config;

import es.inditex.coreplatform.application.usecase.GetPrice;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ControllerMockConfig {
    @Bean
    public GetPrice getPrice() {
        return Mockito.mock(GetPrice.class);
    }
}
