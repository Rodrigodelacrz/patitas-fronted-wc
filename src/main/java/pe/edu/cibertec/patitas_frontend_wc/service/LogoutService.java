package pe.edu.cibertec.patitas_frontend_wc.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_frontend_wc.client.LogoutClient;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_frontend_wc.dto.LogoutResponseDTO;

@Service
public class LogoutService {

    @Autowired
    LogoutClient logoutClient;

    public LogoutResponseDTO logout(LogoutRequestDTO logoutRequestDTO) {
        ResponseEntity<LogoutResponseDTO> response = logoutClient.logout(logoutRequestDTO);

        // Manejo de errores
        if (response.getStatusCode().is2xxSuccessful()) {
            LogoutResponseDTO body = response.getBody();
            if (body != null) {
                return body;
            } else {
                throw new RuntimeException("No se recibió respuesta del servicio de logout");
            }
        } else {
            throw new RuntimeException("Error al cerrar sesión: " + response.getStatusCode());
        }
    }
}
