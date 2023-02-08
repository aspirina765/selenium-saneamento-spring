package net.cloudtecnologia;

import net.cloudtecnologia.client.Chrome;
import net.cloudtecnologia.model.entity.Barcode;
import net.cloudtecnologia.model.entity.Ncm;
import net.cloudtecnologia.service.impl.BarcodeServiceImpl;
import net.cloudtecnologia.service.impl.NcmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Optional;


@SpringBootApplication
public class RoboSaneamentoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoboSaneamentoSpringApplication.class, args);
    }

    private static final String BARCODES = "G:\\Meu Drive\\BACKUP\\TECNOLOGIA DA INFORMAÇÃO (T.I)\\SANEAMENTO LENO\\" +
            "SANEAMENTO.txt";

    private static final String NCMS = "G:\\Meu Drive\\BACKUP\\TECNOLOGIA DA INFORMAÇÃO (T.I)\\SANEAMENTO LENO\\" +
            "Ncms Completo.txt";


    @Autowired
    private NcmServiceImpl ncmService;

    @Autowired
    private BarcodeServiceImpl barcodeService;


    @Bean(name = "executaTestSalvar")
    public CommandLineRunner executaTestSalvar() {
        return args -> {
            salvarBarcode();
            // salvarNCM();
        };
    }


    public void obterCodes() {
        Chrome chrome = new Chrome();
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
    }

    @Transactional
    public void salvarBarcode() {
        try {
            File arq = new File(BARCODES);
            //
            FileInputStream stream = new FileInputStream(arq);
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(streamReader);
            String linha;
            System.out.println("Processando...");
            int registros = 0;
            int sucesso = 0, erro = 0;
            while ((linha = buffer.readLine()) != null) {
                registros++;
                try {
                    String quebra[] = linha.split("[ ]", -1);
                    //
                    String codBarras = quebra[3];
                    String descricao = "";
                    int index;
                    for (index = 4; index <= 21; index++) {
                        String valor = quebra[index];
                        if (valor.length() == 10
                                && valor.substring(4, 5).trim().equals(".")
                                && valor.substring(7, 8).trim().equals(".") ||
                                valor.length() == 13
                                        && valor.substring(4, 5).trim().equals(".")
                                        && valor.substring(7, 8).trim().equals(".")
                                        && valor.substring(10, 11).trim().equals(".")
                        ) {
                            break;
                        } else {
                            descricao = descricao + " " + valor;
                        }
                    }
                    //
                    String ncm = quebra[index].trim();
                    String cst = quebra[index + 1].trim();
                    String aliqC = quebra[index + 2].trim();
                    String alicR = quebra[index + 3].trim();
                    //
                    Barcode bar = new Barcode();
                    bar.setBarcode(codBarras.trim());
                    bar.setDescricao(descricao.trim());
                    //
                    Optional<Ncm> option = ncmService.findByNcm(ncm);
                    if (option.isPresent()) {
                        bar.setNcm(option.get());
                    } else {
                        Ncm novoNcm = ncmService.save(new Ncm(ncm));
                        bar.setNcm(novoNcm);
                    }
                    bar.setCst(cst);
                    bar.setAliq_c(aliqC);
                    bar.setAliq_r(alicR);
                    //
                    System.out.println(registros + " | " + bar.toString());
                    Optional<Barcode> optionBar = barcodeService.findByBarcode(bar.getBarcode());
                    if (optionBar.isPresent()) {
                        System.out.println("IGNORADO: Já existe o Barcode cadastrado: " + bar.getBarcode());
                    } else {
                        barcodeService.save(bar);
                        System.out.println("Salvo! ");
                        sucesso++;
                    }
                    //
                    System.out.println("---------------------------------------");

                } catch (Exception e) {
                    erro++;
                    System.out.println("Nº " + registros + " # Erro ao Extrair  linha! " + linha);
                }

            }
            System.out.println("");
            System.out.println("Total: " + registros);
            System.out.println("Sucesso: " + sucesso);
            System.out.println("Erros: " + erro);
        } catch (IOException e) {
            System.out.println("ERRO AO Ler " + e.getMessage());
        }

    }


    @Transactional
    public void salvarNCM() {


        try {
            File ncms = new File(NCMS);
//
            FileInputStream stream = new FileInputStream(ncms);
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(streamReader);
            String linha;
            System.out.println("Processando...");
            int registros = 0;
            int sucesso = 0, erro = 0;
            while ((linha = buffer.readLine()) != null) {
                registros++;
                //
                try {
                    String quebra[] = linha.split("[#]", -1);
                    //
                    Ncm ncm = new Ncm();
                    //
                    String quebraNcm[] = quebra[0].split("[:]", -1);
                    ncm.setNcm(quebraNcm[1].trim());

                    String quebraDescricao[] = quebra[1].split("[:]", -1);
                    ncm.setDescricao(quebraDescricao[1].trim());

                    String quebraCste[] = quebra[2].split("[:]", -1);
                    ncm.setCste(quebraCste[1].trim());

                    String quebraCsts[] = quebra[3].split("[:]", -1);
                    ncm.setCsts(quebraCsts[1].trim());

                    String quebraPise[] = quebra[4].split("[:]", -1);
                    ncm.setPis_e(quebraPise[1].trim());

                    String quebraPiss[] = quebra[5].split("[:]", -1);
                    ncm.setPis_s(quebraPiss[1].trim());

                    String quebraCofine[] = quebra[6].split("[:]", -1);
                    ncm.setCofin_e(quebraCofine[1].trim());

                    String quebraCofins[] = quebra[7].split("[:]", -1);
                    ncm.setCofin_s(quebraCofins[1].trim());

                    String quebraTipodebito[] = quebra[8].split("[:]", -1);
                    ncm.setTipo_debito(quebraTipodebito[1].trim());

                    String quebraCest[] = quebra[9].split("[:]", -1);
                    ncm.setCest(quebraCest[1].trim());
                    //
                    System.out.println(registros + " | " + ncm.toString());
                    Optional<Ncm> option = ncmService.findByNcm(ncm.getNcm());
                    if (option.isPresent()) {
                        System.out.println("Já existe no banco, ignorando...");
                    } else {
                        ncmService.save(ncm);
                        System.out.println("Salvo...");
                        sucesso++;
                    }
                    System.out.println("------------------------------------------------");
                } catch (Exception e) {
                    System.out.println("*** ERRO:" + linha);
                    erro++;
                }
            }
            System.out.println("");
            System.out.println("Total: " + registros);
            System.out.println("Sucesso: " + sucesso);
            System.out.println("Erros: " + erro);
        } catch (IOException e) {
            System.out.println("ERRO AO Ler " + e.getMessage());
        }

    }

}
