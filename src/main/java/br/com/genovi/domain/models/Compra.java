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
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_compra",
            sequenceName = "seq_compra",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
}
