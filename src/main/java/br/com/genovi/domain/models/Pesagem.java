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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_pesagem")
    @SequenceGenerator(name = "gen_pesagem", sequenceName = "gen_id_pesagem", allocationSize = 1)
    private Long id;

    @Column(name = "data_pesagem")
    private LocalDateTime dataPesagem;

    @ManyToOne
    @JoinColumn(name = "ovino_id", nullable = false)
    private Ovino ovino;

    @Column(name = "peso")
    private Double peso;

    @Override
    public String toString() {
        return "id: " + id +
                ", dataPesagem: " + dataPesagem +
                ", ovino: " + ovino +
                ", peso: " + peso;
    }
}
