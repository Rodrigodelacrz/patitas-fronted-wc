package pe.edu.cibertec.patitas_frontend_wc.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientAutenticacion(WebClient.Builder builder) {
        return createWebClient(builder, "http://localhost:8081/autenticacion");
    }

    @Bean
    public WebClient webClientFinanzas(WebClient.Builder builder) {
        return createWebClient(builder, "http://localhost:8081/finanzas"); // Cambia esta URL según el endpoint real
    }

    @Bean
    public WebClient webClientReportes(WebClient.Builder builder) {
        return createWebClient(builder, "http://localhost:8081/reportes"); // Cambia esta URL según el endpoint real
    }

    private WebClient createWebClient(WebClient.Builder builder, String baseUrl) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // timeout de conexión
                .responseTimeout(Duration.ofSeconds(10)) // timeout de lectura de toda la respuesta
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))); // timeout de lectura de cada paquete

        return builder
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}