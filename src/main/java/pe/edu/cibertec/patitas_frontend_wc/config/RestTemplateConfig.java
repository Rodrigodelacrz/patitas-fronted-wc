package pe.edu.cibertec.patitas_frontend_wc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

            @Bean
            public RestTemplate restTemplateAutenticacion(RestTemplateBuilder builder){
                return builder
                        .rootUri("http://localhost:8081/autenticacion")
                        .setConnectTimeout(Duration.ofSeconds(10))
                        .setReadTimeout(Duration.ofSeconds(10))
                        .build();
            }


            @Bean
            public RestTemplate restTemplateFinanzas(RestTemplateBuilder builder){
                return builder
                    .rootUri("http://localhost:8081/autenticacion")
                    .setConnectTimeout(Duration.ofSeconds(10))
                    .setReadTimeout(Duration.ofSeconds(10))
                    .build();
            }
             @Bean
            public RestTemplate restTemplateReportes(RestTemplateBuilder builder){
                return builder
                   .rootUri("http://localhost:8081/autenticacion")
                   .setConnectTimeout(Duration.ofSeconds(10))
                   .setReadTimeout(Duration.ofSeconds(10))
                   .build();
            }

}
