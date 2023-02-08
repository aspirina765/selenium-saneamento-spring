package net.cloudtecnologia.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Barcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String barcode;

    @Column
    private String descricao;

    @OneToOne
    @JoinColumn(name = "id_ncm")
    private Ncm ncm;

    @Column
    private String cst;

    @Column
    private String aliq_c;

    @Column
    private String aliq_r;

}
