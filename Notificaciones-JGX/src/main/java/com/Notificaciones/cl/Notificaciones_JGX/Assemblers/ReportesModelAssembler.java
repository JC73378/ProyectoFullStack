package com.Notificaciones.cl.Notificaciones_JGX.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.Notificaciones.cl.Notificaciones_JGX.Controller.ReporteController;
import com.Notificaciones.cl.Notificaciones_JGX.Model.Reportes;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReportesModelAssembler implements RepresentationModelAssembler<Reportes, EntityModel<Reportes>> {

   @Override
    public EntityModel<Reportes> toModel(Reportes reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteController.class).buscar(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteController.class).listar()).withRel("reportes"));
    }
}

