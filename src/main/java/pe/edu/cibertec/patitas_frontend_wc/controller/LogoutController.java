package pe.edu.cibertec.patitas_frontend_wc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_frontend_wc.service.LogoutService;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = "http://localhost:5173")
public class LogoutController {


    @Autowired
    LogoutService logoutService;

    @PostMapping("/user/logout")
    public Mono<LogoutResponseDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        // Validar campos de entrada
        if (logoutRequestDTO.tipoDocumento() == null || logoutRequestDTO.tipoDocumento().trim().length() == 0 ||
                logoutRequestDTO.numeroDocumento() == null || logoutRequestDTO.numeroDocumento().trim().length() == 0) {
            return Mono.just(new LogoutResponseDTO("01", "Error: Debe completar correctamente los campos"));
        }

        try {
            // Lógica del servicio de logout
            LogoutResponseDTO response = logoutService.logout(logoutRequestDTO); // Pasar el DTO real al servicio
            if (response.codigo().equals("00")) {
                return Mono.just(new LogoutResponseDTO("00", "Logout exitoso"));
            } else {
                return Mono.just(new LogoutResponseDTO("02", "Error: Logout fallido"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.just(new LogoutResponseDTO("99", "Error: Ocurrió un problema en el logout"));
        }
    }

}