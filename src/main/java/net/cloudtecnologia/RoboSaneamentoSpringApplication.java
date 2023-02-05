package net.cloudtecnologia;

import net.cloudtecnologia.client.Chrome;
import net.cloudtecnologia.service.impl.BlueSoftServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RoboSaneamentoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoboSaneamentoSpringApplication.class, args);
    }

    @Autowired
    private BlueSoftServiceimpl serviceimpl;

    @Bean(name = "executaTestSalvar")
    public CommandLineRunner executaTestSalvar() {
        return args -> {
            Chrome chrome = new Chrome();
            //
            try {
                chrome.confiugurarChrome();
                chrome.logarWlGestao();
                chrome.acessarSaneamentoDeProdutos();
                chrome.paginarTabela();
                chrome.encerrarNavegador();
            } catch (Exception e) {
                System.out.println("Erro inesperado!");
                chrome.encerrarNavegador();
            }
        };
    }


}
