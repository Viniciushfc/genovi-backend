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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_compra")
    @SequenceGenerator(name = "gen_compra", sequenceName = "gen_id_compra", allocationSize = 1)
    private Long id;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    @Override
    public String toString() {
        return "id: " + id +
                ", dataCompra: " + dataCompra +
                ", valor: " + valor +
                ", vendedor: " + vendedor;
    }
}
