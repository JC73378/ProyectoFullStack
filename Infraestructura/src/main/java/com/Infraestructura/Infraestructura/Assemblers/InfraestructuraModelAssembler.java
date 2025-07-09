package com.Infraestructura.Infraestructura.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.Infraestructura.Infraestructura.Controller.InfraestructuraController;
import com.Infraestructura.Infraestructura.Model.InfraestructuraModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InfraestructuraModelAssembler implements RepresentationModelAssembler<InfraestructuraModel, EntityModel<InfraestructuraModel>> {

   @Override
    public EntityModel<InfraestructuraModel> toModel(InfraestructuraModel infraestructuraModel) {
        return EntityModel.of(infraestructuraModel,
                linkTo(methodOn(InfraestructuraController.class).buscarporId(infraestructuraModel.getId())).withSelfRel(),
                linkTo(methodOn(InfraestructuraController.class).buscarinfraestructura()).withRel("infraestructura"));
    }
}

