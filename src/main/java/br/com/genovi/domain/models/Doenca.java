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
@Table(name = "doenca")
public class Doenca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_doenca")
    @SequenceGenerator(name = "gen_doenca", sequenceName = "gen_id_doenca", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Override
    public String toString() {
        return "id: " + id +
                ", nome: " + nome +
                ", descricao: " + descricao;
    }
}
