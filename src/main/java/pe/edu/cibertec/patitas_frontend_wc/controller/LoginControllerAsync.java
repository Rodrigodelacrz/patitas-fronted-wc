package pe.edu.cibertec.patitas_frontend_wc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patitas_frontend_wc.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_frontend_wc.viewmodel.LoginModel;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginControllerAsync {

    @Autowired
    WebClient webClientAutenticacion;

    @PostMapping("/autenticar-async")
    public Mono<LoginResponseDTO> autenticar(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Validar campos de entrada
        if (loginRequestDTO.tipoDocumento() == null || loginRequestDTO.tipoDocumento().trim().isEmpty() ||
                loginRequestDTO.numeroDocumento() == null || loginRequestDTO.numeroDocumento().trim().isEmpty() ||
                loginRequestDTO.password() == null || loginRequestDTO.password().trim().isEmpty()) {
            return Mono.just(new LoginResponseDTO("01", "Error: Debe completar correctamente sus credenciales", "", ""));
        }

        return webClientAutenticacion.post()
                .uri("/login")
                .body(Mono.just(loginRequestDTO), LoginRequestDTO.class)
                .retrieve()
                .bodyToMono(LoginResponseDTO.class)
                .flatMap(response -> {
                    if (response.codigo().equals("00")) {
                        return Mono.just(new LoginResponseDTO("00", "", response.nombreUsuario(), ""));
                    } else {
                        return Mono.just(new LoginResponseDTO("02", "Error: Autenticación fallida", "", ""));
                    }
                })
                .onErrorResume(e -> {
                    System.out.println("Error en autenticación: " + e.getMessage());
                    return Mono.just(new LoginResponseDTO("99", "Error: Ocurrió un problema en la autenticación", "", ""));
                });
    }

    @PostMapping("/logout")
    public Mono<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        return webClientAutenticacion.post()
                .uri("/autenticacion/logout")  // RUTA
                .body(Mono.just(logoutRequestDTO), LogoutRequestDTO.class)
                .retrieve()
                .bodyToMono(LogoutResponseDTO.class)
                .flatMap(response -> {
                    if (response.resultado()) {
                        System.out.println("Cierre de sesión exitoso: " + response.fecha());
                    } else {
                        System.out.println("Error al cerrar sesión: " + response.mensajeError());
                    }
                    return Mono.just(response);
                })
                .onErrorResume(e -> {
                    System.out.println("Error en cierre de sesión: " + e.getMessage());
                    return Mono.just(new LogoutResponseDTO(false, new Date(), "Error: Ocurrió un problema en el cierre de sesión"));
                });
    }
}