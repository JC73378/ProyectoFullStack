package com.Notificaciones.cl.Notificaciones_JGX;
import com.Notificaciones.cl.Notificaciones_JGX.Controller.ReporteController;
import com.Notificaciones.cl.Notificaciones_JGX.Model.Reportes;
import com.Notificaciones.cl.Notificaciones_JGX.Service.ReportesService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportesService reportesService;

    @Autowired
    private ObjectMapper objectMapper;

    
    @DisplayName("✅ testListar: debería devolver una lista con reportes")
    @Test
public void testListar() throws Exception {
    System.out.println("testListar ejecutado correctamente");
    Reportes reporte = new Reportes();
    reporte.setId(1);
    reporte.setNombre_profesor("Profesor A");
    reporte.setTipo_notificacion("Email");
    reporte.setNivel_urgencia("Alta");
    reporte.setFecha(null);
    reporte.setDetalle_notificacion("Detalle 1");

    List<Reportes> reportes = List.of(reporte);

    when(reportesService.findAll()).thenReturn(reportes);

    mockMvc.perform(get("/api/reportes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre_profesor").value("Profesor A"));
}

    @DisplayName("✅ testGuardar: debería guardar un reporte y devolverlo")
   @Test
public void testGuardar() throws Exception {
    System.out.println("tesGuardar ejecutado correctamente");
    Reportes reporte = new Reportes();
    reporte.setId(null);
    reporte.setNombre_profesor("Profesor B");
    reporte.setTipo_notificacion("SMS");
    reporte.setNivel_urgencia("Media");
    reporte.setFecha(null);
    reporte.setDetalle_notificacion("Detalle 2");

    Reportes reporteGuardado = new Reportes();
    reporteGuardado.setId(2);
    reporteGuardado.setNombre_profesor("Profesor B");
    reporteGuardado.setTipo_notificacion("SMS");
    reporteGuardado.setNivel_urgencia("Media");
    reporteGuardado.setFecha(null);
    reporteGuardado.setDetalle_notificacion("Detalle 2");

    when(reportesService.saveReporte(reporte)).thenReturn(reporteGuardado);

    mockMvc.perform(post("/api/reportes/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reporte)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.nombre_profesor").value("Profesor B"));
}
    
    @DisplayName("✅ testBuscar: debería buscar un reporte por ID y devolverlo")

    @Test
    public void testBuscar() throws Exception {
        System.out.println("testBuscar ejecutado correctamente");
        Integer id = 1;
        Reportes reporte = new Reportes();
        reporte.setId(id);
        reporte.setNombre_profesor("Profesor A");
        reporte.setTipo_notificacion("Email");
        reporte.setNivel_urgencia("Alta");
        reporte.setFecha(null);
        reporte.setDetalle_notificacion("Detalle 1");

        when(reportesService.findById(id)).thenReturn(Optional.of(reporte));

        mockMvc.perform(get("/api/reportes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_profesor").value("Profesor A"));
    }
    @DisplayName("✅ testActualizar: debería actualizar un reporte y devolverlo, Test funcionando correctamente")
    @Test
    public void testActualizar() throws Exception {
        System.out.println("testActualizar ejecutado correctamente");
        Integer id = 1;

        Reportes reporteExistente = new Reportes();
        reporteExistente.setId(id);
        reporteExistente.setNombre_profesor("Profesor A");
        reporteExistente.setTipo_notificacion("Email");
        reporteExistente.setNivel_urgencia("Alta");
        reporteExistente.setFecha(null);
        reporteExistente.setDetalle_notificacion("Detalle 1");

        Reportes reporteActualizado = new Reportes();
        reporteActualizado.setId(id);
        reporteActualizado.setNombre_profesor("Profesor C");
        reporteActualizado.setTipo_notificacion("Push");
        reporteActualizado.setNivel_urgencia("Baja");
        reporteActualizado.setFecha(null);
        reporteActualizado.setDetalle_notificacion("Detalle actualizado");

        when(reportesService.findById(id)).thenReturn(Optional.of(reporteExistente));
        when(reportesService.saveReporte(reporteExistente)).thenReturn(reporteActualizado);

        mockMvc.perform(put("/api/reportes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre_profesor").value("Profesor C"))
                .andExpect(jsonPath("$.tipo_notificacion").value("Push"))
                .andExpect(jsonPath("$.nivel_urgencia").value("Baja"));
    }
    @DisplayName("✅ testEliminar: debería eliminar un reporte por ID")
    @Test
    public void testEliminar() throws Exception {
        System.out.println("testEliminar ejecutado correctamente");
        Integer id = 1;

        doNothing().when(reportesService).deleteById(id);

        mockMvc.perform(delete("/api/v2/reportes/{id}", id))
                .andExpect(status().isNoContent());
    }
}
