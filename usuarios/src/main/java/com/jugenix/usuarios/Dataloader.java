package com.jugenix.usuarios;

import com.github.javafaker.Faker;
import com.jugenix.usuarios.model.Usuario;
import com.jugenix.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Carga usuarios falsos al iniciar la app
public class Dataloader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final Faker faker = new Faker();

    public Dataloader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setCorreo(faker.internet().emailAddress());
            usuario.setTelefono(faker.phoneNumber().cellPhone());
            usuario.setRol(faker.options().option("TECNICO", "DOCENTE", "ESTUDIANTE"));

            usuarioRepository.save(usuario);

            System.out.println("Usuario " + i + ": " + usuario.getNombre() +
                    " | " + usuario.getCorreo() +
                    " | " + usuario.getTelefono() +
                    " | " + usuario.getRol());
        }
    }
}