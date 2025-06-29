package com.Notificaciones.cl.Notificaciones_JGX;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Notificaciones.cl.Notificaciones_JGX.Model.Reportes;
import com.Notificaciones.cl.Notificaciones_JGX.Repository.NotificacionRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class Dataloader implements CommandLineRunner {
    private final NotificacionRepository notificacionRepository;
    private final Faker faker = new Faker();

    private final String[] tipos = {"Incidente", "Retraso", "Inasistencia", "Evaluación", "Otro"};
    private final String[] niveles = {"Alta", "Media", "Baja"};
    private final String[] detalles = {"Examen Transversal", "Clase remota", "Prueba diagnostico",
            "Examen de recuperación", "Cambio de sala"};

    public Dataloader(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 12; i++) {
            Reportes reporte = new Reportes();

            reporte.setNombre_profesor(faker.name().fullName());
            reporte.setTipo_notificacion(faker.options().option(tipos));
            reporte.setNivel_urgencia(faker.options().option(niveles));
            reporte.setDetalle_notificacion(faker.options().option(detalles));
            reporte.setFecha(convertToLocalDate(faker.date().between(
                    java.sql.Date.valueOf(LocalDate.now().minusMonths(12)),
                    java.sql.Date.valueOf(LocalDate.now())
            )));

            notificacionRepository.save(reporte);

            System.out.println("Registro " + (i + 1) + ":");
            System.out.println("Nombre Profesor: " + reporte.getNombre_profesor());
            System.out.println("Tipo Notificación: " + reporte.getTipo_notificacion());
            System.out.println("Nivel Urgencia: " + reporte.getNivel_urgencia());
            System.out.println("Fecha: " + reporte.getFecha());
            System.out.println("Detalle: " + reporte.getDetalle_notificacion());
            System.out.println("---------------------------------------");
        }
    }

    private static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}