package net.cloudtecnologia.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Ncm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 13, unique = true)
    private String ncm;

    @Column
    private String descricao;

    @Column
    private String cste;

    @Column
    private String csts;

    @Column
    private String pis_e;

    @Column
    private String pis_s;

    @Column
    private String cofin_e;

    @Column
    private String cofin_s;

    @Column
    private String tipo_debito;

    @Column
    private String cest;

    
    public Ncm(String ncm) {
        this.ncm = ncm;
    }
}
