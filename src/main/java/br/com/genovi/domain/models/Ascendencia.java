package br.com.genovi.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ascendencia")
public class Ascendencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ascendencia")
    @SequenceGenerator(name = "gen_ascendencia", sequenceName = "gen_id_ascendencia", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ovino_pai")
    private Ovino pai;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ovino_mae")
    private Ovino mae;

    @Override
    public String toString() {
        return "id: " + id +
                ", pai: " + pai +
                ", mae: " + mae;
    }
}
