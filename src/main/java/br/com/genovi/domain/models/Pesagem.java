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
@Table(name = "pesagem")
public class Pesagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_pesagem",
            sequenceName = "seq_pesagem",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "data_pesagem")
    private LocalDateTime dataPesagem;

    @ManyToOne
    @JoinColumn(name = "ovino_id", nullable = false)
    private Ovino ovino;
}
