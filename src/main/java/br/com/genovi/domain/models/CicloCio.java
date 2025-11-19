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
@Table(name = "ciclo_Cio")
public class CicloCio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ciclo_cio")
    @SequenceGenerator(name = "gen_ciclo_cio", sequenceName = "gen_id_ciclo_cio", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovelha")
    private Ovino ovelha;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "observacoes")
    private String observacoes;
}
