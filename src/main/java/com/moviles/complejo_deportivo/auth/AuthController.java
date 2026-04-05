package com.moviles.complejo_deportivo.auth;

import com.moviles.complejo_deportivo.dto.RegisterRequest;
import com.moviles.complejo_deportivo.entidades.Usuario;
import com.moviles.complejo_deportivo.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controlador para la autenticación y registro de usuarios.
 * Proporciona endpoints para login y registro, así como manejo de errores de autenticación.
 * Utiliza JWT para la generación de tokens y roles para la autorización.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    /**
     * AuthenticationManager de Spring Security para autenticar usuarios.
     */
    private final AuthenticationManager authManager;
    /**
     * Repositorio para la gestión de usuarios.
     */
    private final UsuarioRepository usuarioRepo;
    /**
     * Servicio para la gestión de JWT.
     */
    private final JwtService jwt;
    /**
     * PasswordEncoder para encriptar contraseñas.
     */
    private final PasswordEncoder passwordEncoder;

    /**
    * Lista de roles quedamos
    **/
    List<String> roles = List.of("ROLE_DEFAULT_USER");

    /**
     * Endpoint para el login de usuarios.
     * Autentica las credenciales y retorna un token JWT si son válidas.
     * @param req mapa con username y password
     * @return mapa con el token, tipo y roles
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String correo = req.get("correo");
        String contrasena = req.get("contrasena");

        // Si las credenciales son incorrectas, lanza AuthenticationException → 401 por defecto
        authManager.authenticate(new UsernamePasswordAuthenticationToken(correo, contrasena));

        var user = usuarioRepo.findByCorreo(correo).orElseThrow();
        String token = jwt.generate(user.getNombre(), roles);

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", roles
        );
    }

    /**
     * Endpoint para el registro de nuevos usuarios.
     * Valida los datos, asigna roles y retorna un token JWT.
     * @param req datos de registro (correo, contraseña, roles)
     * @return mapa con el token, tipo y roles
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@RequestBody RegisterRequest req) {

        if (usuarioRepo.findByCorreo(req.getCorreo()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya existe");
        }

        // Crear usuario con todos los campos obligatorios
        Usuario user = new Usuario();
        user.setNombre(req.getNombre());
        user.setCorreo(req.getCorreo());
        user.setContrasena(passwordEncoder.encode(req.getContrasena()));
        user.setFecha_creacion(LocalDateTime.now());

        usuarioRepo.save(user);

        String token = jwt.generate(user.getCorreo(), roles);

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", roles
        );
    }

    /**
     * Maneja errores de autenticación devolviendo un mensaje estándar.
     * @param e excepción de autenticación
     * @return mapa con el error
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public Map<String, String> onAuthError(Exception e) {
        return Map.of("error", "Bad credentials");
    }
}
