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
@Table(name = "ocorrencia_Doenca")
public class OcorrenciaDoenca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @ManyToOne
    @JoinColumn(name = "id_doenca")
    private Doenca doenca;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_final")
    private LocalDateTime dataFinal;

    @Column(name = "curada")
    private boolean curada;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Funcionario responsavel;
}
