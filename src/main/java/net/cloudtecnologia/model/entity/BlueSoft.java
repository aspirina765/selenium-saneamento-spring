package net.cloudtecnologia.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BlueSoft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String barcode;

    @Column
    private String descricao;

    @Column
    private String ncm;

    @Column
    private String cst;

    @Column
    private String aliq_c;

    @Column
    private String alic_r;

    public BlueSoft(String barcode, String descricao, String ncm, String cst, String aliq_c, String alic_r) {
        this.barcode = barcode;
        this.descricao = descricao;
        this.ncm = ncm;
        this.cst = cst;
        this.aliq_c = aliq_c;
        this.alic_r = alic_r;
    }

}
