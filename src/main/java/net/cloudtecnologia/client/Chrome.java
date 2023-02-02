package net.cloudtecnologia.client;

import net.cloudtecnologia.model.entity.BlueSoft;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Chrome {

    private static final String URL_LOGIN = "http://wlcontabilidade.sytes.net:8080/wlgestao/login.jsf";
    private static final String URL_SANEAMENTO_PRODUTOS = "http://wlcontabilidade.sytes.net:8080/wlgestao/paginas/saneamento/commom/produtossaneamento.jsf";
    private static final String USER = "anilson-asj";
    private static final String PASS = "115200";
    private static final String CAMINHO_TXT = "C:\\Users\\thiago.melo\\Desktop\\SANEAMENTO.txt";

    public WebDriver retornarNaveghador() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\thiago.melo\\Downloads\\chromedriver.exe");
        WebDriver navegador = new org.openqa.selenium.chrome.ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        return navegador;
    }

    public WebDriver logarWlGestao(WebDriver navegador) {
        navegador.get(URL_LOGIN);
        navegador.findElement(By.xpath("//*[@id=\"j_username\"]")).sendKeys(USER);
        navegador.findElement(By.xpath("//*[@id=\"j_password\"]")).sendKeys(PASS);
        navegador.findElement(By.xpath("//*[@id=\"j_idt15\"]")).click();
        return navegador;
    }

    public WebDriver acessarSaneamentoDeProdutos(WebDriver navegador) {
        navegador.get(URL_SANEAMENTO_PRODUTOS);
        return navegador;
    }

    public List<BlueSoft> paginarTabela(WebDriver navegador) {
        List<BlueSoft> listaObj = new ArrayList<>();
        //
        Scanner ler = new Scanner(System.in);
        System.out.println("Pressione 'ENTER' para começar a paginação!");
        ler.next();
        int totalRegistros = 0;
        int max = 4644;
        for (int i = 1; i <= max; i++) {
            String info = navegador.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/span[1]")).getText();
            System.out.println("Processando HTML:");
            System.out.println(info);
            //
            List<WebElement> lista = navegador.findElement(By.className("ui-datatable-tablewrapper"))
                    .findElements(By.tagName("tr"));
            System.out.println("");
            System.out.println("  INFO: ");
            System.out.println("");
            int cont = 0;
            for (WebElement elemento : lista) {
                if (cont > 0) {
                    totalRegistros++;
                    List<WebElement> colunas = elemento.findElements(By.tagName("td"));
                    //
                    try {
                        BlueSoft blu = new BlueSoft(colunas.get(4).getText(), colunas.get(5).getText(),
                                colunas.get(6).getText(), colunas.get(7).getText(),
                                colunas.get(8).getText(), colunas.get(9).getText());
                        listaObj.add(blu);
                        System.out.println(blu.toString());
                    } catch (Exception e) {
                        System.out.println("***Erro ao obter informações colunas***");
                    }
                }
                cont++;
            }
            navegador.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/a[3]")).click();
            System.out.println("");
            System.out.println("Clicou na ABA: " + (i + 1));
            System.out.println("");
            System.out.println("---------------------------------------------------------------");
            System.out.println("");
            aguardarSeconds(10);
        }//fim do laço
        return listaObj;
    }


    public void aguardarSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
