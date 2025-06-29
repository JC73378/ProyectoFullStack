package com.Notificaciones.cl.Notificaciones_JGX.Controller;
import com.Notificaciones.cl.Notificaciones_JGX.Model.Reportes;
import com.Notificaciones.cl.Notificaciones_JGX.Service.ReportesService;
import com.Notificaciones.cl.Notificaciones_JGX.Assemblers.ReportesModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/Reportes")
public class ReportesControllerV2 {
      @Autowired
    private ReportesService reportesService;

    @Autowired
    private ReportesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reportes>> getAllReportes() {
        List<EntityModel<Reportes>> reportes = reportesService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reportes,
                linkTo(methodOn(ReportesControllerV2.class).getAllReportes()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reportes>> getReporteById(@PathVariable Integer id) {
        Optional<Reportes> reporte = reportesService.findById(id);
        return reporte.map(value -> ResponseEntity.ok(assembler.toModel(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reportes>> createReporte(@RequestBody Reportes reporte) {
        Reportes nuevoReporte = reportesService.saveReporte(reporte);
        return ResponseEntity
                .created(linkTo(methodOn(ReportesControllerV2.class).getReporteById(nuevoReporte.getId())).toUri())
                .body(assembler.toModel(nuevoReporte));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reportes>> updateReporte(@PathVariable Integer id, @RequestBody Reportes reporte) {
        Optional<Reportes> optional = reportesService.findById(id);
        if (optional.isPresent()) {
            Reportes existente = optional.get();
            existente.setNombre_profesor(reporte.getNombre_profesor());
            existente.setTipo_notificacion(reporte.getTipo_notificacion());
            existente.setNivel_urgencia(reporte.getNivel_urgencia());
            existente.setFecha(reporte.getFecha());
            existente.setDetalle_notificacion(reporte.getDetalle_notificacion());

            Reportes actualizado = reportesService.saveReporte(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteReporte(@PathVariable Integer id) {
        try {
            reportesService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
