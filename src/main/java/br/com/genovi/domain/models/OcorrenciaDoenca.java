package br.com.genovi.domain.models;

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
@Table(name = "ocorrencia_doenca")
public class OcorrenciaDoenca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_ocorrencia_doenca",
            sequenceName = "seq_ocorrencia_doenca",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @ManyToOne
    @JoinColumn(name = "id_doenca")
    private Doenca doenca;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_final", nullable = true)
    private LocalDateTime dataFinal;

    @Column(name = "is_curada", nullable = true)
    private Boolean curada;
}
