package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.EnumReproducao;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_reproducao")
    @SequenceGenerator(name = "gen_reproducao", sequenceName = "gen_id_reproducao", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carneiro_pai")
    private Ovino carneiroPai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovelha_mae")
    private Ovino ovelhaMae;

    @Column(name = "reproducao")
    @Enumerated(EnumType.STRING)
    private EnumReproducao enumReproducao;

    @Column(name = "data_reproducao")
    private LocalDateTime dataReproducao;

    @Override
    public String toString() {
        return "id: " + id +
                ", carneiroPai: " + carneiroPai +
                ", ovelhaMae: " + ovelhaMae +
                ", enumReproducao: " + enumReproducao +
                ", dataReproducao: " + dataReproducao;
    }
}
