package com.Notificaciones.cl.Notificaciones_JGX.Controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Notificaciones.cl.Notificaciones_JGX.Model.Reportes;
import com.Notificaciones.cl.Notificaciones_JGX.Service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;


@RestController
@Tag(name = "Reportes", description = "Operaciones relacionadas con los reportes de notificaciones")
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReportesService reportesService;

  @GetMapping
  @Operation(summary = "Listar todos los reportes", description = "Obtiene una lista de todos los reportes de notificaciones.")
  public ResponseEntity<List<Reportes>> listar() {
    List<Reportes> reportes = reportesService.findAll();
    if(reportes.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(reportes);
  }

  @PostMapping("/crear")
  @Operation(summary = "Crear un nuevo reporte", description = "Guarda un nuevo reporte en la base de datos.")
  public ResponseEntity<Reportes> guardar(@RequestBody Reportes reporte) {
     Reportes reportenuevo= reportesService.saveReporte(reporte);
      return ResponseEntity.status(HttpStatus.CREATED).body(reportenuevo);
}



  @GetMapping("/{id}")
  @Operation(summary = "Buscar reporte por ID", description = "Obtiene un reporte específico por su ID.")
    public ResponseEntity<Reportes> buscar(@PathVariable Integer id) {
      Optional<Reportes> reporteOptional = reportesService.findById(id);
      if (reporteOptional.isPresent()) {
        return ResponseEntity.ok(reporteOptional.get());
      } else {
        return ResponseEntity.notFound().build();
    }
  }


     @PutMapping("/{id}")
    @Operation(summary = "Actualizar reporte", description = "Actualiza un reporte existente por su ID.")
        public ResponseEntity<Reportes> actualizar(@PathVariable Integer id, @RequestBody Reportes reporte) {
        Optional<Reportes> reporteOptional = reportesService.findById(id);
          if (reporteOptional.isPresent()) {
        Reportes reporteExistente = reporteOptional.get();
        reporteExistente.setNombre_profesor(reporte.getNombre_profesor());
        reporteExistente.setTipo_notificacion(reporte.getTipo_notificacion());
        reporteExistente.setNivel_urgencia(reporte.getNivel_urgencia());
        reporteExistente.setFecha(reporte.getFecha());
        reporteExistente.setDetalle_notificacion(reporte.getDetalle_notificacion());

        reportesService.saveReporte(reporteExistente);
        return ResponseEntity.ok(reporteExistente);
    } else {
        return ResponseEntity.notFound().build();
    }
}

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar reporte", description = "Elimina un reporte específico por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
      try {
        reportesService.deleteById(id);
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}
}