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
@Table(name = "vendedor")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_vendedor",
            sequenceName = "seq_vendedor",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpfCnpj", unique = true)
    private String cpfCnpj;

    @Column(name = "email")
    private String email;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;
}
