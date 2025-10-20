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
@Table(name = "gestacao")
public class Gestacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_gestacao",
            sequenceName = "seq_gestacao",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovelha_mae")
    private Ovino ovelhaMae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovelha_PAI")
    private Ovino ovelhaPai;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reproducao", nullable = true)
    private Reproducao reproducao;

    @Column(name = "data_gestacao")
    private LocalDateTime dataGestacao;
}
