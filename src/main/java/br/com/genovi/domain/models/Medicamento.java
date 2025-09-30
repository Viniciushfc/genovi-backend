package br.com.genovi.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "fabricante")
    private String fabricante;

    @ManyToMany
    @JoinTable(
            name = "medicamento_doenca",
            joinColumns = @JoinColumn(name = "medicamento_id"),
            inverseJoinColumns = @JoinColumn(name = "doenca_id")
    )
    private List<Doenca> doencas;

    @Column(name = "doce_unica")
    private boolean doceUnica;

    @Column(name = "quantidade_doses")
    private Integer quantidadeDoses;

    @Column(name = "is_vacina")
    private boolean isVacina;
}
