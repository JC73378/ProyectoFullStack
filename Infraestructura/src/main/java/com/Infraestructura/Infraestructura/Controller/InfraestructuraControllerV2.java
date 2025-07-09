package com.Infraestructura.Infraestructura.Controller;

import com.Infraestructura.Infraestructura.Model.InfraestructuraModel;
import com.Infraestructura.Infraestructura.Service.InfraestructuraService;
import com.Infraestructura.Infraestructura.Assemblers.InfraestructuraModelAssembler;

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
@RequestMapping("/api/v2/infraestructuras")
public class InfraestructuraControllerV2 {

    @Autowired
    private InfraestructuraService infraestructuraService;

    @Autowired
    private InfraestructuraModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<InfraestructuraModel>> getAll() {
        List<EntityModel<InfraestructuraModel>> infraestructuras = infraestructuraService.buscarinfraestructura()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(infraestructuras,
                linkTo(methodOn(InfraestructuraControllerV2.class).getAll()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InfraestructuraModel>> getById(@PathVariable Long id) {
        Optional<InfraestructuraModel> optional = infraestructuraService.buscarporId(id);
        return optional.map(value -> ResponseEntity.ok(assembler.toModel(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InfraestructuraModel>> create(@RequestBody InfraestructuraModel infraestructura) {
        InfraestructuraModel nueva = infraestructuraService.createInfraestructura(infraestructura);
        return ResponseEntity
                .created(linkTo(methodOn(InfraestructuraControllerV2.class).getById(nueva.getId())).toUri())
                .body(assembler.toModel(nueva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InfraestructuraModel>> update(@PathVariable Long id, @RequestBody InfraestructuraModel updated) {
        Optional<InfraestructuraModel> optional = infraestructuraService.buscarporId(id);
        if (optional.isPresent()) {
            InfraestructuraModel existente = optional.get();
            existente.setNombre(updated.getNombre());
            existente.setCapacidad(updated.getCapacidad());
            existente.setDisponible(updated.getDisponible());

            InfraestructuraModel guardada = infraestructuraService.updateInfraestructura(id, existente);
            return ResponseEntity.ok(assembler.toModel(guardada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        infraestructuraService.deleteInfraestructura(id);
        return ResponseEntity.noContent().build();
    }
}
