package org.practica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EntityScan("org.practica.model")
//@ComponentScan("org.practica.controllers")
public class Server_Start{
    public static void main(String[] args) {
        SpringApplication.run(Server_Start.class, args);
    }
}
