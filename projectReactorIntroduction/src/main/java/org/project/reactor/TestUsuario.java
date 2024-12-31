package org.project.reactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class TestUsuario {
    private static final Logger log = LoggerFactory.getLogger(TestUsuario.class);
    public static void main(String[] args) {
        Flux<String> nombres = Flux.just("Christian Ramirez", "Mery Ramirez", "Ludwin Jhurgo");
        Flux<Usuario> usuarios = nombres
                .map(name -> {
                    String upper = name.toUpperCase();
                    return new Usuario(upper.split(" ")[0],upper.split(" ")[1]);
                })
                .filter(usuario -> usuario.getName().equalsIgnoreCase("Ludwin"))
                .doOnNext(usuario -> {
                    if (usuario == null) {
                        throw  new RuntimeException("Los nombres no pueden estar vacios");
                    }
                    System.out.println(usuario);
                })
                .map(usuario -> {
                    String name = usuario.getName().toLowerCase();
                    usuario.setName(name);
                    return usuario;
                });

        usuarios.subscribe(
                e -> log.info(e.toString()),
                error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Se ha finalizado la ejecución del observvable con éxito!");
                    }
                }
        );

    }
}
