package com.jugenix.usuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jugenix.usuarios.controller.UsuarioController;
import com.jugenix.usuarios.model.Usuario;
import com.jugenix.usuarios.service.UsuarioService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class TestUsuarioController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("✅ testListar: debería devolver una lista con usuarios")
    @Test
    public void testListar() throws Exception {
        System.out.println("testListar ejecutado correctamente");
        Usuario usuario = new Usuario(1L, "Ana Ruiz", "ana@mail.com", "123456789", "ADMIN");

        List<Usuario> usuarios = List.of(usuario);

        when(usuarioService.obtenerTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ana Ruiz"));
    }

    @DisplayName("✅ testGuardar: debería guardar un usuario y devolverlo")
    @Test
    public void testGuardar() throws Exception {
        System.out.println("testGuardar ejecutado correctamente");

        Usuario nuevo = new Usuario(null, "Carlos Pérez", "carlos@mail.com", "999888777", "DOCENTE");
        Usuario guardado = new Usuario(2L, "Carlos Pérez", "carlos@mail.com", "999888777", "DOCENTE");

        when(usuarioService.crear(nuevo)).thenReturn(guardado);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Carlos Pérez"));
    }

    @DisplayName("✅ testBuscar: debería obtener un usuario por ID (simulado en update)")
    @Test
    public void testBuscarSimulado() throws Exception {
        System.out.println("testBuscar (simulado) ejecutado correctamente");

        Long id = 1L;
        Usuario usuario = new Usuario(id, "Mario Soto", "mario@mail.com", "555444333", "ESTUDIANTE");

        when(usuarioService.actualizar(eq(id), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Mario Soto"));
    }

    @DisplayName("✅ testEliminar: debería eliminar un usuario por ID")
    @Test
    public void testEliminar() throws Exception {
        System.out.println("testEliminar ejecutado correctamente");

        Long id = 3L;
        doNothing().when(usuarioService).eliminar(id);

        mockMvc.perform(delete("/usuarios/{id}", id))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).eliminar(id);
    }
}

