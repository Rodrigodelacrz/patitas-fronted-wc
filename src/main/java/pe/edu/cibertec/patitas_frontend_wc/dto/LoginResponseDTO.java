package pe.edu.cibertec.patitas_frontend_wc.dto;


//@JsonIgnoreProperties(ignoreUnknown = true)
public record LoginResponseDTO(String codigo, String mensaje, String nombreUsuario, String correoUsuario) {
}
