package com.moviles.complejo_deportivo.dto;

import lombok.Data;

/**
 * CrearUsuarioInDTO
 *
 * DTO utilizado para la creación de un nuevo usuario dentro del sistema.
 * Contiene la información necesaria para registrar un usuario, incluyendo sus datos personales,
 * credenciales de acceso.
 *
 * Este objeto se utiliza como cuerpo de una solicitud POST en el controlador
 * correspondiente a la entidad Usuario.
 *
 * Ejemplo JSON de solicitud:
 * {
 *   "nombre": "Juan Pérez",
 *   "correo": "juan.perez@genomebank.com",
 *   "contrasena": "12345Segura!",
 * }
 *
 * Campos importantes:
 * - **email:** debe ser único por usuario.
 *
 * @author Proyecto Móviles
 * @version 1.0
 * @since 2026
 */

@Data
public class CrearUsuarioInDTO {
    private String nombre;
    private String correo;
    private String contrasena;
}
