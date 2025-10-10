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
@Table(name = "parto")
public class Parto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_parto",
            sequenceName = "seq_parto",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovino_mae")
    private Ovino ovinoMae;

    @ManyToOne
    @JoinColumn(name = "id_ovino_pai")
    private Ovino ovinoPai;

    @OneToOne
    @JoinColumn(name = "id_gestacao", nullable = true)
    private Gestacao gestacao;

    @Column(name = "data_parto")
    private LocalDateTime dataParto;
}
