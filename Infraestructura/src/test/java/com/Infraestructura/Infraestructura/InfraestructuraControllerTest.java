package com.Infraestructura.Infraestructura;
import com.Infraestructura.Infraestructura.Controller.InfraestructuraController;
import com.Infraestructura.Infraestructura.Model.InfraestructuraModel;
import com.Infraestructura.Infraestructura.Service.InfraestructuraService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(InfraestructuraController.class)
public class InfraestructuraControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfraestructuraService infraestructuraService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("✅ GET /api/infraestructuras: Listar infraestructuras")
    @Test
    public void testListarInfraestructuras() throws Exception {
        InfraestructuraModel infra = new InfraestructuraModel(1L, "Sala A", 40, true);
        when(infraestructuraService.buscarinfraestructura()).thenReturn(List.of(infra));

        mockMvc.perform(get("/api/infraestructuras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Sala A"))
                .andExpect(jsonPath("$[0].capacidad").value(40))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @DisplayName("✅ GET /api/infraestructuras/{id}: Obtener por ID")
    @Test
    public void testGetInfraestructuraById() throws Exception {
        InfraestructuraModel infra = new InfraestructuraModel(1L, "Gimnasio", 100, false);
        when(infraestructuraService.buscarporId(1L)).thenReturn(Optional.of(infra));

        mockMvc.perform(get("/api/infraestructuras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Gimnasio"))
                .andExpect(jsonPath("$.capacidad").value(100))
                .andExpect(jsonPath("$.disponible").value(false));
    }

    @DisplayName("✅ POST /api/infraestructuras/crear: Crear infraestructura")
    @Test
    public void testCrearInfraestructura() throws Exception {
        InfraestructuraModel nueva = new InfraestructuraModel(null, "Laboratorio", 60, true);
        InfraestructuraModel creada = new InfraestructuraModel(2L, "Laboratorio", 60, true);

        when(infraestructuraService.createInfraestructura(nueva)).thenReturn(creada);

        mockMvc.perform(post("/api/infraestructuras/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Laboratorio"));
    }

    @DisplayName("✅ PUT /api/infraestructuras/{id}: Actualizar infraestructura")
    @Test
    public void testActualizarInfraestructura() throws Exception {
        InfraestructuraModel existente = new InfraestructuraModel(1L, "Sala A", 40, true);
        InfraestructuraModel actualizada = new InfraestructuraModel(1L, "Sala A Renovada", 50, false);

        when(infraestructuraService.buscarporId(1L)).thenReturn(Optional.of(existente));
        when(infraestructuraService.updateInfraestructura(1L, actualizada)).thenReturn(actualizada);

        mockMvc.perform(put("/api/infraestructuras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Sala A Renovada"))
                .andExpect(jsonPath("$.capacidad").value(50))
                .andExpect(jsonPath("$.disponible").value(false));
    }

    @DisplayName("✅ DELETE /api/infraestructuras/{id}: Eliminar infraestructura")
    @Test
    public void testEliminarInfraestructura() throws Exception {
        doNothing().when(infraestructuraService).deleteInfraestructura(1L);

        mockMvc.perform(delete("/api/infraestructuras/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("✅ PATCH /api/infraestructuras/{id}/indisponible: Marcar como no disponible")
    @Test
    public void testSetInfraestructuraIndisponible() throws Exception {
        InfraestructuraModel infraIndisponible = new InfraestructuraModel(1L, "Cancha", 200, false);

        when(infraestructuraService.InfraestructuraIndisponible(1L)).thenReturn(Optional.of(infraIndisponible));

        mockMvc.perform(patch("/api/infraestructuras/1/indisponible"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.disponible").value(false));
    }

}
