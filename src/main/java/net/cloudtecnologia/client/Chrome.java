package net.cloudtecnologia.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


@Configuration
public class Chrome {

    private static final String URL_LOGIN = "http://wlcontabilidade.sytes.net:8080/wlgestao/login.jsf";
    private static final String URL_SANEAMENTO_PRODUTOS = "http://wlcontabilidade.sytes.net:8080/wlgestao/paginas/saneamento/commom/produtossaneamento.jsf";
    private static final String USER = "anilson-asj";
    private static final String PASS = "115200";
    private int registros;
    private static final String TXT = "C:\\Users\\thiago.melo\\Desktop\\SANEAMENTO.txt";

    private static final int INDEX_START = 1;

    WebDriver chrome;
    BufferedWriter escreve;

    public void confiugurarChrome() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\thiago.melo\\Downloads\\chromedriver.exe");
        chrome = new org.openqa.selenium.chrome.ChromeDriver();
        chrome.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
    }

    public void logarWlGestao() {
        chrome.get(URL_LOGIN);
        chrome.findElement(By.xpath("//*[@id=\"j_username\"]")).sendKeys(USER);
        chrome.findElement(By.xpath("//*[@id=\"j_password\"]")).sendKeys(PASS);
        chrome.findElement(By.xpath("//*[@id=\"j_idt15\"]")).click();
    }

    public void acessarSaneamentoDeProdutos() {
        chrome.get(URL_SANEAMENTO_PRODUTOS);

    }

    public void paginarTabela() {
        Scanner ler = new Scanner(System.in);
        System.out.println("Digite 'OK' para seguir!");
        ler.next();
        //
        String maxStr = informacaoPaginaAtual();
        maxStr = maxStr.substring(maxStr.indexOf("of") + 2, maxStr.length()).trim();
        int max = Integer.parseInt(maxStr);
        for (int index = INDEX_START; index <= max; index++) {
            boolean processar = false;
            while (processar == false) {
                String infoAtual = informacaoPaginaAtual();
                System.out.println("----------------------------( " + infoAtual + " )---------------------------- ");
                int pageAtual = retornarPaginaAtual();
                System.out.println(" pageAtual: " + pageAtual);
                System.out.println(" index:     " + index);
                if (index == pageAtual) {
                    processar = true;
                    System.out.println(" Processar: " + processar);
                    //
                    escreverTxt();
                    //
                    proximaPagina(index + 1);
                } else {
                    System.out.println("  Processando novamente a página: " + index);
                    proximaPagina(index);
                }
            }
        }
        System.out.println("------------- CONCLUÍDO ----------------");
        fecharBufferEscrita();
    }


    public void fecharBufferEscrita() {
        try {
            escreve.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String informacaoPaginaAtual() {
        return chrome.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/span[1]"))
                .getText()
                .replace("(", "").replace(")", "").trim();
    }


    public int retornarPaginaAtual() {
        String pageAtualStr = chrome.findElement(By.xpath("//*[@id=\"formSaneamento:listaProdutosEmpresa_paginator_top\"]/span[1]"))
                .getText()
                .replace("(", "").replace(")", "").trim();

        pageAtualStr = pageAtualStr.substring(0, pageAtualStr.indexOf("of")).trim();
        return Integer.parseInt(pageAtualStr);
    }


    public void proximaPagina(int netxtPage) {
        System.out.println(" netxtPage: " + netxtPage);
        WebElement paginator = chrome.findElement(By.className("ui-paginator-pages"));
        List<WebElement> pages = paginator.findElements(By.tagName("a"));
        //
        for (WebElement page : pages) {
            if (page.getText().equals("" + netxtPage)) {
                page.click();
            }
        }
        System.out.println("");
        aguardarSeconds(10);
    }

    public void aguardarSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void escreverTxt() {
        List<WebElement> tabela = chrome.findElement(By.className("ui-datatable-tablewrapper"))
                .findElements(By.tagName("tr"));
        System.out.println("Escrevendo...");
        System.out.println("");
        //
        try {
            escreve = new BufferedWriter(new FileWriter((TXT), true));
            //
            int cont = 0;
            for (WebElement objeto : tabela) {
                if (cont > 0) {
                    registros++;
                    String text = objeto.getText().trim();
                    System.out.println(registros + "  ->  " + text);
                    escreve.write(text);
                    escreve.newLine();
                    escreve.flush();
                }
                cont++;
            }
            //
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("");
    }


    public void encerrarNavegador() {
        chrome.quit();
        chrome.close();
    }

}
