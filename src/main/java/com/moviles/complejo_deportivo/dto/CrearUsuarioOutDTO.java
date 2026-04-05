package com.moviles.complejo_deportivo.dto;

import lombok.Data;

/**
 * CrearUsuarioOutDTO
 *
 * DTO utilizado como respuesta tras la creación de un nuevo usuario en el sistema.
 * Contiene la información generada y confirmada por el sistema luego del registro exitoso
 * del usuario, incluyendo su identificador, nombre de usuario, y email.
 *
 * Este objeto se devuelve típicamente como resultado de una operación POST
 * en el controlador correspondiente a la entidad Usuario.
 *
 * Ejemplo JSON de respuesta:
 * {
 *   "id": 1,
 *   "nombre": "Juan Pérez",
 *   "correo": "juan.perez@genomebank.com",
 * }
 **
 * @author Proyecto Móviles
 * @version 1.0
 * @since 2026
 */

@Data
public class CrearUsuarioOutDTO {
    private String nombre;
    private String correo;
}
