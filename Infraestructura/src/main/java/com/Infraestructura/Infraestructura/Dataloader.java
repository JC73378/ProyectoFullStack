package com.Infraestructura.Infraestructura;

import com.Infraestructura.Infraestructura.Model.InfraestructuraModel;
import com.Infraestructura.Infraestructura.Repository.InfraestructuraRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Dataloader {

    @Bean
    public ApplicationRunner loadData(InfraestructuraRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new InfraestructuraModel(null, "Aula Magna", 120, true));
                repo.save(new InfraestructuraModel(null, "Laboratorio de Física", 30, true));
                repo.save(new InfraestructuraModel(null, "Sala de Conferencias", 80, false));
                repo.save(new InfraestructuraModel(null, "Cancha Techada", 300, true));
                repo.save(new InfraestructuraModel(null, "Biblioteca Central", 150, true));
                repo.save(new InfraestructuraModel(null, "Auditorio Principal", 200, false));
                repo.save(new InfraestructuraModel(null, "Gimnasio", 100, true));
                repo.save(new InfraestructuraModel(null, "Cafetería", 70, true));
                repo.save(new InfraestructuraModel(null, "Sala de Reuniones", 25, false));
                repo.save(new InfraestructuraModel(null, "Laboratorio de Computación", 40, true));
                repo.save(new InfraestructuraModel(null, "Estacionamiento", 500, true));
                repo.save(new InfraestructuraModel(null, "Zona de Descanso", 60, true));
                repo.save(new InfraestructuraModel(null, "Sala de Estudio", 35, false));
                System.out.println("Datos de infraestructuras cargados.");
            } else {
                System.out.println("Ya existen datos en la tabla Infraestructura.");
            }
        };
    }
}