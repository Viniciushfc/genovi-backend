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
@Table(name = "aplicacao")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_aplicacao",
            sequenceName = "seq_aplicacao",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @Column(name = "data_aplicacao")
    private LocalDateTime dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;

    @Column(name = "data_proxima_dose")
    private LocalDateTime dataProximaDose;
}
