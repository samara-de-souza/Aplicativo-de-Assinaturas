package br.AppAssinatura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.AppAssinatura.persistencia.*;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IAplicativoRepository aplicativoRepository;

    @Autowired
    private IAssinaturaRepository assinaturaRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Inicializando clientes
            List<Cliente> clientes = Arrays.asList(
                new Cliente(1L, "Alice Silva", "alice.silva@example.com"),
                new Cliente(2L, "Bruno Lima", "bruno.lima@example.com"),
                new Cliente(3L, "Carla Souza", "carla.souza@example.com"),
                new Cliente(4L, "Daniel Costa", "daniel.costa@example.com"),
                new Cliente(5L, "Eva Rocha", "eva.rocha@example.com"),
                new Cliente(6L, "Fernando Melo", "fernando.melo@example.com"),
                new Cliente(7L, "Gabriela Oliveira", "gabriela.oliveira@example.com"),
                new Cliente(8L, "Henrique Santos", "henrique.santos@example.com"),
                new Cliente(9L, "Isabela Pereira", "isabela.pereira@example.com"),
                new Cliente(10L, "Jorge Almeida", "jorge.almeida@example.com")
            );
            clienteRepository.saveAll(clientes);

            // Inicializando aplicativos
            List<Aplicativo> aplicativos = Arrays.asList(
                new Aplicativo(1L, "App 1", 9.99),
                new Aplicativo(2L, "App 2", 14.99),
                new Aplicativo(3L, "App 3", 19.99),
                new Aplicativo(4L, "App 4", 24.99),
                new Aplicativo(5L, "App 5", 29.99)
            );
            aplicativoRepository.saveAll(aplicativos);

            // Inicializando assinaturas
            List<Assinatura> assinaturas = Arrays.asList(
                new Assinatura(1L, aplicativos.get(0), clientes.get(0), new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)),
                new Assinatura(2L, aplicativos.get(1), clientes.get(1), new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)),
                new Assinatura(3L, aplicativos.get(2), clientes.get(2), new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)),
                new Assinatura(4L, aplicativos.get(3), clientes.get(3), new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)),
                new Assinatura(5L, aplicativos.get(4), clientes.get(4), new Date(), new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
            );
            assinaturaRepository.saveAll(assinaturas);
        };
    }
}
