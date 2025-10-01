package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.TypeReproducao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reproducao")
public class Reproducao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_reproducao",
            sequenceName = "seq_reproducao",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_carneiro_pai")
    private Ovino carneiroPai;

    @ManyToOne
    @JoinColumn(name = "id_ovelha_mae")
    private Ovino ovelhaMae;

    @Column(name = "reproducao")
    private TypeReproducao typeReproducao;

    @Column(name = "data_reproducao")
    private LocalDateTime dataReproducao;

    @Column(name = "observacoes")
    private String observacoes;

}
