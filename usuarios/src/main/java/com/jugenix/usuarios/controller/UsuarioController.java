package com.jugenix.usuarios.controller;

import com.jugenix.usuarios.model.Usuario;
import com.jugenix.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca como controlador REST
@RequestMapping("/usuarios") // Ruta base del microservicio
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios del sistema")
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    // Crear un nuevo usuario
    @PostMapping
    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario por ID", description = "Actualiza un usuario existente por su ID")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario datos) {
        return usuarioService.actualizar(id, datos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario por su ID")
    public Usuario buscarPorid(@PathVariable Long id) {
        return usuarioService.buscarPorIdOptional(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    // Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por ID", description = "Elimina un usuario por su ID")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
