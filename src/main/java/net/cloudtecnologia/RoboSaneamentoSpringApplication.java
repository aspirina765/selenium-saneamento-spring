package net.cloudtecnologia;

import net.cloudtecnologia.client.Chrome;
import net.cloudtecnologia.model.entity.BlueSoft;
import net.cloudtecnologia.service.impl.BlueSoftServiceimpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RoboSaneamentoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoboSaneamentoSpringApplication.class, args);
    }

    @Autowired
    private BlueSoftServiceimpl service;


    @Bean(name = "executaTestSalvar")
    public CommandLineRunner executaTestSalvar() {
        return args -> {

            Chrome chrome = new Chrome();
            WebDriver navegador = chrome.retornarNaveghador();
            //
            navegador = chrome.logarWlGestao(navegador);
            navegador = chrome.acessarSaneamentoDeProdutos(navegador);
            List<BlueSoft> listaObj = chrome.paginarTabela(navegador);
            //
            service.saveAll(listaObj);
            //

        };
    }


}
